import { Component, OnInit } from '@angular/core';
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
  carouselIndex = 0;
  filtreFormTable: FormGroup;
  filtreFormJeu: FormGroup;
  reservationForm: FormGroup;
  tableChoisi = false;
  jeuChoisi = false;
  jeux: Jeu[] = [];
  tableSelectionne?: TableJeu;
  jeuSelectionne?:Jeu;
  clientId?: any;
  filtresAppliques = false;
  today: string = new Date().toISOString().split('T')[0]; // yyyy-mm-dd
  dateDebut: Date=new Date();  // stocke la date/heure d'arrivée
  dateFin: Date=new Date();

  constructor(
    private tableJeuService: TableJeuService,
    private jeuService: JeuService,
    private authService: AuthService,
    private clientService: ClientService,
    private reservationService: ReservationService
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
      dateFinAffichage: new FormControl(null, Validators.required)
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
    this.filtreFormTable.valueChanges.subscribe(() => {
      this.tableChoisi = false;
      this.tableSelectionne = undefined;
      this.jeux = [];
      this.carouselIndex = 0;
      this.scrollCarousel();
      this.filtresAppliques = false;
    });
    
    
  }
  prev(): void {
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

    this.jeuService.findDisponibles(this.dateDebut.toISOString().substring(0, 10)).subscribe((data) => {
      this.jeux = data.filter((jeu) => {
        let ok = true;
        // nbPersonnes choisi sur table
        ok =
          ok &&
          jeu.nbJoueurMinimum <= filtreTable.nbPersonnes &&
          jeu.nbJoueurMaximum >= filtreTable.nbPersonnes;

        const dureeReservation = (this.dateFin.getTime() - this.dateDebut.getTime()) / (1000 * 60);
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
    this.carouselIndex = 0;
    this.scrollCarousel();
  }

  choisirJeu(jeu: Jeu) {


    this.jeuSelectionne = jeu;
    this.jeuChoisi = true;
    console.log("la date du jeu ", this.dateFin)

    this.reservationForm.patchValue({
      nbJoueur: this.filtreFormTable.value.nbPersonnes,
      dateDebutAffichage: this.toInputFormat(this.dateDebut), // string pour l’affichage
      dateFinAffichage: this.toInputFormat(this.dateFin),
    });
  }

  validerReservation() {
    if (this.reservationForm.invalid || !this.tableSelectionne || !this.jeuSelectionne) return;

    const form = this.reservationForm.value;
    console.log("Date debut AAAA", form.dateDebutAffichage);
    console.log("type date debut ",this.dateDebut);
    console.log("table jeu ",this.tableSelectionne);
    console.log("Nb de joueur ",form.nbJoueur);
    console.log("client id ",this.clientId);
    console.log("Le jeu ",this.jeuSelectionne);
    const reservationDto = new Reservation(
      0,
      this.dateDebut,
      this.dateFin,
      form.nbJoueur,
      this.tableSelectionne,
      this.jeuSelectionne,
      StatutReservation.CONFIRMEE, // statutReservation
      {id:this.clientId} as any, // clientId
      null! // gameMasterId
    );
    console.log("Reservation : ",reservationDto);
    console.log(reservationDto instanceof Reservation); 

    this.reservationService.save(reservationDto);
    console.log("ici ok");
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


}
