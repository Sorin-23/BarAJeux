import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TableJeuService } from '../../service/table-jeu-service';
import { TableJeu } from '../../dto/table-jeu';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Jeu } from '../../dto/jeu';
import { JeuService } from '../../service/jeu-service';
import { AuthService } from '../../service/auth-service';
import { ClientService } from '../../service/client-service';
import { ReservationService } from '../../service/reservation-service';
import { Reservation } from '../../dto/reservation';
import { StatutReservation } from '../../dto/enum/statut-reservation';
import { Employe } from '../../dto/employe';
import { EmployeService } from '../../service/employe-service';
@Component({
  selector: 'app-table-page',
  imports: [
    CommonModule,
    CarouselModule,
    ButtonModule,
    TagModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  templateUrl: './table-page.html',
  styleUrl: './table-page.css',
})
export class TablePage implements OnInit {
  tables: TableJeu[] = [];
  @ViewChild('carrouselTables') carrouselTables!: ElementRef<HTMLDivElement>;
  @ViewChild('carrouselJeux') carrouselJeux!: ElementRef<HTMLDivElement>;
  carouselIndexTables = 0;
  carouselIndexJeux = 0;
  //carouselIndex = 0;
  filtreFormTable: FormGroup;
  filtreFormJeu: FormGroup;
  reservationForm: FormGroup;
  tableChoisi = false;
  jeuChoisi = false;
  jeux: Jeu[] = [];
  tableSelectionne?: TableJeu;
  jeuSelectionne?: Jeu;
  clientId?: any;
  filtresAppliques = false;
  today: string = new Date().toISOString().split('T')[0]; // yyyy-mm-dd
  dateDebut: Date = new Date(); // stocke la date/heure d'arrivée
  dateFin: Date = new Date();
  gameMasters: Employe[] = [];
  reservations: Reservation[] = [];
  gameMastersDispo: Employe[] = [];

  constructor(
    private tableJeuService: TableJeuService,
    private jeuService: JeuService,
    private authService: AuthService,
    private clientService: ClientService,
    private reservationService: ReservationService,
    private employeService: EmployeService
  ) {
    this.filtreFormTable = new FormGroup({
      nbPersonnes: new FormControl(null, [Validators.required]),
      date: new FormControl(null, [Validators.required]),
      heureArrivee: new FormControl(null, [Validators.required]),
      heureDepart: new FormControl(null, [Validators.required]),
    });
    this.filtreFormJeu = new FormGroup({
      ageMin: new FormControl(null),
    });
    this.reservationForm = new FormGroup({
      nbJoueur: new FormControl(null, Validators.required),
      dateDebutAffichage: new FormControl(null, Validators.required), // string pour <input>
      dateFinAffichage: new FormControl(null, Validators.required),
      avecGameMaster: new FormControl(false),
      gameMasterId: new FormControl(null),
    });
  }

  ngOnInit(): void {
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data;
    });
    this.jeuService.findAll().subscribe((data) => {
      this.jeux = data;
    });
    const username = this.authService.username;

    this.clientService.findByUsername(username).subscribe((client) => {
      this.clientId = client.id;
    });

    this.employeService.getAllGameMasters().subscribe((gms) => {
      this.gameMasters = gms;
      console.log('Tous les Game Masters:', this.gameMasters);
    });

    // Récupérer toutes les réservations pour calculer les indisponibilités
    this.reservationService.findAll().subscribe((res) => {
      this.reservations = res;
      console.log('Toutes les réservations:', this.reservations);
    });
    this.filtreFormTable.valueChanges.subscribe(() => {
      this.tableChoisi = false;
      this.tableSelectionne = undefined;
      this.jeux = [];
      this.carouselIndexTables = 0;
      this.carouselIndexJeux = 0;
      this.scrollCarousel(this.carrouselTables.nativeElement, this.carouselIndexTables);
      this.scrollCarousel(this.carrouselJeux.nativeElement, this.carouselIndexJeux);
      this.filtresAppliques = false;
    });
    this.reservationForm.valueChanges.subscribe((form) => {
      if (form.avecGameMaster && form.dateDebutAffichage && form.dateFinAffichage) {
        this.updateGameMastersDispo(
          new Date(form.dateDebutAffichage),
          new Date(form.dateFinAffichage)
        );
      } else {
        this.gameMastersDispo = [];
      }
    });
  }
  /*prev(): void {
    if (this.carouselIndex > 0) {
      this.carouselIndex--;
    }
    this.scrollCarousel();
  }

  next(): void {
    if (this.carouselIndex < this.tables.length - 1) {
      this.carouselIndex++;
    }
    this.scrollCarousel();
  }

  scrollCarousel(): void {
    const carousel = document.querySelector('.carrousel') as HTMLElement;
    const cardWidth = carousel.children[0]?.clientWidth || 0;
    carousel.scrollLeft = this.carouselIndex * (cardWidth + 20); // 20 = gap
  }*/
  prevTables() {
    if (this.carouselIndexTables > 0) this.carouselIndexTables--;
    this.scrollCarousel(this.carrouselTables.nativeElement, this.carouselIndexTables);
  }

  nextTables() {
    if (this.carouselIndexTables < this.tables.length - 1) this.carouselIndexTables++;
    this.scrollCarousel(this.carrouselTables.nativeElement, this.carouselIndexTables);
  }

  prevJeux() {
    if (this.carouselIndexJeux > 0) this.carouselIndexJeux--;
    this.scrollCarousel(this.carrouselJeux.nativeElement, this.carouselIndexJeux);
  }

  nextJeux() {
    if (this.carouselIndexJeux < this.jeux.length - 1) this.carouselIndexJeux++;
    this.scrollCarousel(this.carrouselJeux.nativeElement, this.carouselIndexJeux);
  }

  scrollCarousel(carousel: HTMLElement, index: number) {
    const cardWidth = carousel.children[0]?.clientWidth || 0;
    carousel.scrollLeft = index * (cardWidth + 20); // 20 = gap
  }

  appliquerFiltreTable() {
    const filtre = this.filtreFormTable.value;
    // vérification de la logique heure date

    const today = new Date();
    today.setHours(0, 0, 0, 0); // ignore l'heure

    const dateChoisie = new Date(filtre.date);
    dateChoisie.setHours(0, 0, 0, 0);

    if (dateChoisie < today) {
      alert("La date choisie doit être aujourd'hui ou dans le futur !");
      return;
    }

    const now = new Date();

    if (dateChoisie.getTime() === today.getTime()) {
      const heureActuelle = now.getHours() * 60 + now.getMinutes();

      const [hA, mA] = filtre.heureArrivee.split(':').map(Number);
      const arriveeMin = hA * 60 + mA;

      if (arriveeMin < heureActuelle) {
        alert("L'heure d'arrivée doit être supérieure à l'heure actuelle.");
        return;
      }

      const [hD, mD] = filtre.heureDepart.split(':').map(Number);
      const departMin = hD * 60 + mD;

      if (departMin <= heureActuelle) {
        alert("L'heure de départ doit être supérieure à l'heure actuelle.");
        return;
      }
    }

    const [hArrivee, mArrivee] = filtre.heureArrivee.split(':').map(Number);
    const [hDepart, mDepart] = filtre.heureDepart.split(':').map(Number);

    this.dateDebut = this.combineDateTime(filtre.date, filtre.heureArrivee);
    //this.dateDebut.setHours(hArrivee, mArrivee, 0, 0);

    this.dateFin = this.combineDateTime(filtre.date, filtre.heureDepart);
    //this.dateFin.setHours(hDepart, mDepart, 0, 0);

    // Vérifier que l'heure de départ est après l'heure d'arrivée
    if (this.dateFin <= this.dateDebut) {
      alert("L'heure de départ doit être supérieure à l'heure d'arrivée !");
      return;
    }

    //convertir la date et heures du formulaire en type date-heure
    let filtreDebut: Date | null = null;
    let filtreFin: Date | null = null;

    if (filtre.date) {
      filtreDebut = new Date(filtre.date);
      if (filtre.heureArrivee) {
        const [hArrivee, mArrivee] = filtre.heureArrivee.split(':').map(Number);
        filtreDebut.setHours(hArrivee, mArrivee, 0, 0);
      }
      if (filtre.heureDepart) {
        filtreFin = new Date(filtre.date);
        const [hDepart, mDepart] = filtre.heureDepart.split(':').map(Number);
        filtreFin.setHours(hDepart, mDepart, 0, 0);
      }
    }
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data.filter((table) => {
        let ok = true;
        if (filtre.nbPersonnes) {
          ok = ok && table.capacite >= filtre.nbPersonnes;
          console.log('=> rejetée pour nbPersonnes');
        }
        // ici on peut ajouter date et heure plus tard
        if (filtreDebut) {
          console.log('filtreDebut:', filtreDebut);
          const reservations = table.reservations || [];
          console.log('Table:', table.nomTable, 'reservations:', table.reservations);

          reservations.forEach((res) => {
            const debut = new Date(res.datetimeDebut);
            const fin = new Date(res.datetimeFin);
            console.log('reservation:', debut, fin);

            if (filtreFin) {
              if (filtreDebut.getTime() < fin.getTime() && filtreFin.getTime() > debut.getTime()) {
                ok = false;
                console.log(
                  '=> rejetée pour chevauchement avec filtreDebut:',
                  filtreDebut,
                  'filtreFin:',
                  filtreFin
                );
              }
            } else {
              if (filtreDebut.getTime() < fin.getTime()) {
                ok = false;
                console.log('=> rejetée pour chevauchement sans filtreFin');
              }
            }
          });
        }

        return ok;
      });
    });
    this.filtresAppliques = true;
  }

  choisirTable(tableJeu: TableJeu) {
    if (this.filtreFormTable.invalid || !this.filtresAppliques) {
      alert('Veuillez appliquer et remplir tous les champs avant de filtrer.');
      return;
    }

    const filtreTable = this.filtreFormTable.value;
    /*const dateDebut = new Date(filtreTable.date);
    const dateFin = new Date(filtreTable.date);
    const [hArrivee, mArrivee] = filtreTable.heureArrivee.split(':').map(Number);
    dateDebut.setHours(hArrivee, mArrivee, 0, 0);

    const [hDepart, mDepart] = filtreTable.heureDepart.split(':').map(Number);
    dateFin.setHours(hDepart, mDepart, 0, 0);*/

    this.jeuService
      .findDisponibles(this.dateDebut.toISOString().substring(0, 10))
      .subscribe((data) => {
        this.jeux = data.filter((jeu) => {
          let ok = true;
          // nbPersonnes choisi sur table
          ok =
            ok &&
            jeu.nbJoueurMinimum <= filtreTable.nbPersonnes &&
            jeu.nbJoueurMaximum >= filtreTable.nbPersonnes;

          const dureeReservation =
            (this.dateFin.getTime() - this.dateDebut.getTime()) / (1000 * 60);
          ok = ok && jeu.duree <= dureeReservation;
          return ok;
        });
      });

    this.tableSelectionne = tableJeu;
    this.tableChoisi = true;
  }

  appliquerFiltreJeu() {
    const filtre = this.filtreFormJeu.value;

    this.jeux = this.jeux.filter((jeu) => {
      let ok = true;

      if (filtre.ageMin) {
        ok = ok && jeu.ageMinimum >= filtre.ageMin;
      }

      return ok;
    });

    // reset carousel après filtrage
    this.carouselIndexTables = 0;
    this.carouselIndexJeux = 0;
    this.scrollCarousel(this.carrouselTables.nativeElement, this.carouselIndexTables);
    this.scrollCarousel(this.carrouselJeux.nativeElement, this.carouselIndexJeux);
  }

  choisirJeu(jeu: Jeu) {
    this.jeuSelectionne = jeu;
    this.jeuChoisi = true;
    console.log('la date du jeu ', this.dateFin);

    this.reservationForm.patchValue({
      nbJoueur: this.filtreFormTable.value.nbPersonnes,
      dateDebutAffichage: this.toInputFormat(this.dateDebut),
      dateFinAffichage: this.toInputFormat(this.dateFin),
    });

    // Met à jour les GM disponibles si la checkbox est cochée
    this.updateDispoIfNeeded();
  }

  validerReservation() {
    if (this.reservationForm.invalid || !this.tableSelectionne || !this.jeuSelectionne) return;

    const form = this.reservationForm.value;
    console.log('Date debut AAAA', form.dateDebutAffichage);
    console.log('type date debut ', this.dateDebut);
    console.log('table jeu ', this.tableSelectionne);
    console.log('Nb de joueur ', form.nbJoueur);
    console.log('client id ', this.clientId);
    console.log('Le jeu ', this.jeuSelectionne);

    let gameMasterSelectionne: Employe | null = null;
    if (form.avecGameMaster) {
      const gmId = Number(form.gameMasterId); // <-- conversion
      gameMasterSelectionne = this.gameMastersDispo.find((gm) => gm.id === gmId) || null;

      if (!gameMasterSelectionne) {
        alert('Veuillez sélectionner un Game Master valide !');
        return;
      }
    }
    console.log('AAAAA', form.avecGameMaster);

    const reservationDto = new Reservation(
      0,
      this.dateDebut,
      this.dateFin,
      form.nbJoueur,
      this.tableSelectionne,
      this.jeuSelectionne,
      StatutReservation.CONFIRMEE,
      { id: this.clientId } as any,
      gameMasterSelectionne!
    );
    console.log('Reservation : ', reservationDto);
    console.log(reservationDto instanceof Reservation);

    this.reservationService.save(reservationDto);
    alert('Réservation créée avec succès !');
    this.tableChoisi = false;
    this.tableSelectionne = undefined;
    this.jeuChoisi = false;
    this.jeuSelectionne = undefined;
    this.filtreFormTable.reset();
    this.loadTables();
  }
  private combineDateTime(dateStr: string, timeStr: string): Date {
    const [year, month, day] = dateStr.split('-').map(Number);
    const [hours, minutes] = timeStr.split(':').map(Number);

    // mois -1 car Date utilise 0=janvier
    return new Date(year, month - 1, day, hours, minutes, 0);
  }

  private toInputFormat(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}`; // <-- espace ici
  }

  getGameMastersDisponibles(dateDebut: Date, dateFin: Date): Employe[] {
    if (!this.gameMasters || !this.reservations) return [];

    return this.gameMasters.filter((gm) => {
      // Vérifie si ce game master est libre pendant le créneau choisi
      const estOccupe = this.reservations.some((res) => {
        if (!res.gameMaster) return false; // pas de GM affecté
        if (res.gameMaster.id !== gm.id) return false;

        const resDebut = new Date(res.datetimeDebut);
        const resFin = new Date(res.datetimeFin);

        // Vérifie le chevauchement
        return dateDebut < resFin && dateFin > resDebut;
      });

      return !estOccupe; // on garde ceux qui ne sont pas occupés
    });
  }

  updateGameMastersDispo(dateDebut: Date, dateFin: Date) {
    this.gameMastersDispo = this.gameMasters.filter((gm) => {
      const estOccupe = this.reservations.some(
        (res) =>
          res.gameMaster?.id === gm.id &&
          dateDebut < new Date(res.datetimeFin) &&
          dateFin > new Date(res.datetimeDebut)
      );
      return !estOccupe;
    });

    if (this.gameMastersDispo.length === 0) {
      console.warn('Aucun Game Master disponible pour ce créneau !');
    }
  }
  updateDispoIfNeeded() {
    const form = this.reservationForm.value;
    if (form.avecGameMaster && form.dateDebutAffichage && form.dateFinAffichage) {
      this.updateGameMastersDispo(
        new Date(form.dateDebutAffichage),
        new Date(form.dateFinAffichage)
      );
    } else {
      this.gameMastersDispo = [];
    }
  }

  loadTables() {
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data;
      this.carouselIndexTables = 0;
      this.carouselIndexJeux = 0;
      this.scrollCarousel(this.carrouselTables.nativeElement, this.carouselIndexTables);
      this.scrollCarousel(this.carrouselJeux.nativeElement, this.carouselIndexJeux);
    });
  }
  resetFiltreTable() {
    this.filtreFormTable.reset();
    this.tableChoisi = false;
    this.tableSelectionne = undefined;
    this.jeux = [];
    this.carouselIndexTables = 0;
    this.carouselIndexJeux = 0;
    this.filtresAppliques = false;
    this.loadTables();
  }
}
