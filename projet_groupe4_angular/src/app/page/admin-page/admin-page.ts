import { Component, OnInit } from '@angular/core';
import { Badge } from '../../dto/badge';
import { Employe } from '../../dto/employe';
import { Jeu } from '../../dto/jeu';
import { Reservation } from '../../dto/reservation';
import { TableJeu } from '../../dto/table-jeu';
import { BadgeService } from '../../service/badge-service';
import { EmployeService } from '../../service/employe-service';
import { JeuService } from '../../service/jeu-service';
import { ReservationService } from '../../service/reservation-service';
import { TableJeuService } from '../../service/table-jeu-service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSortModule, Sort } from '@angular/material/sort';
import { EmpruntService } from '../../service/emprunt-service';
import { StatutReservation } from '../../dto/enum/statut-reservation';
import { StatutLocation } from '../../dto/enum/statut-location';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { CategorieJeu } from '../../dto/enum/categorie-jeu';
import { TypeJeu } from '../../dto/enum/type-jeu';
import { Emprunt } from '../../dto/emprunt';
import { AvisService } from '../../service/avis-service';

@Component({
  selector: 'app-admin-page',
  imports: [CommonModule, ReactiveFormsModule, MatSortModule, MatButtonModule],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.css',
})
export class AdminPage implements OnInit {
  data: { [key: string]: any[] } = {
    jeux: [],
    employes: [],
    tables: [],
    badges: [],
    reservations: [],
    emprunts: [],
    avis: [],
  };

  filteredItems: { [key: string]: any[] } = {
    jeux: [],
    employes: [],
    tables: [],
    badges: [],
    reservations: [],
    emprunts: [],
    avis: [],
  };
  lastSort: { [key: string]: Sort | null } = {};

  searchControls: { [key: string]: FormControl } = {
    jeux: new FormControl(''),
    employes: new FormControl(''),
    tables: new FormControl(''),
    badges: new FormControl(''),
    reservations: new FormControl(''),
    emprunts: new FormControl(''),
    avis: new FormControl(''),
  };

  currentEdit: { [key: string]: any } = {
    jeu: null,
    employe: null,
    table: null,
    badge: null,
    reservation: null,
    emprunt: null,
    avis: null,
  };

  statutControls: { [key: string]: FormControl } = {
    reservations: new FormControl(''),
    emprunts: new FormControl(''),
    avis: new FormControl(''),
  };

  enumOptions: { [key: string]: any[] } = {
    typesJeux: Object.values(TypeJeu),
    categoriesJeux: Object.values(CategorieJeu),
    statutReservation: Object.values(StatutReservation),
    statutLocation: Object.values(StatutLocation),
  };

  reservationsDuJour: number = 0;
  editMode = false;
  currentSection = 'dashboard';
  reservationForm!: FormGroup;
  badgeForm!: FormGroup;
  jeuForm!: FormGroup;
  tableForm!: FormGroup;
  employeForm!: FormGroup;
  empruntForm!: FormGroup;

  constructor(
    private jeuService: JeuService,
    private employeService: EmployeService,
    private tableJeuService: TableJeuService,
    private badgeService: BadgeService,
    private reservationService: ReservationService,
    private empruntService: EmpruntService,
    private formBuilder: FormBuilder,
    private avisService: AvisService
  ) {}

  ngOnInit(): void {
    this.loadSection('jeux', this.jeuService);
    this.loadSection('employes', this.employeService);
    this.loadSection('tables', this.tableJeuService);
    this.loadSection('badges', this.badgeService);
    this.loadSection('reservations', this.reservationService);
    this.loadSection('emprunts', this.empruntService);
    this.loadSection('avis', this.avisService);

    Object.keys(this.searchControls).forEach((section) => {
      this.searchControls[section].valueChanges.subscribe((term) => {
        this.filter(section, term);
      });
    });
    Object.keys(this.statutControls).forEach((section) => {
      this.statutControls[section].valueChanges.subscribe(() => {
        this.filter(section, this.searchControls[section].value);
      });
    });

    this.reservationForm = new FormGroup({
      statutReservation: new FormControl(null, { validators: Validators.required }),
    });
    this.empruntForm = new FormGroup({
      statutEmprunt: new FormControl(null, { validators: Validators.required }),
      dateRetourReel: new FormControl(null),
    });
    this.badgeForm = new FormGroup({
      nomBadge: new FormControl(null, { validators: Validators.required }),
      pointMin: new FormControl(null, { validators: Validators.required }),
      imgURL: new FormControl(null, { validators: Validators.required }),
    });
    this.employeForm = new FormGroup({
      nom: new FormControl(null, { validators: Validators.required }),
      prenom: new FormControl(null, { validators: Validators.required }),
      mail: new FormControl(null, { validators: [Validators.required, Validators.email] }),
      telephone: new FormControl(null, { validators: Validators.required }),
      job: new FormControl(null, { validators: Validators.required }),
      gameMaster: new FormControl(false),
    });
    this.jeuForm = new FormGroup({
      nom: new FormControl(null, { validators: Validators.required }),
      typesJeux: new FormControl(null, { validators: Validators.required }),
      ageMinimum: new FormControl(0, { validators: Validators.required }),
      nbJoueurMinimum: new FormControl(1, { validators: Validators.required }),
      nbJoueurMaximum: new FormControl(1, { validators: Validators.required }),
      duree: new FormControl(1, { validators: Validators.required }),
      nbExemplaire: new FormControl(1, { validators: Validators.required }),
      note: new FormControl(null, { validators: Validators.required }),
      categoriesJeux: new FormControl(null, { validators: Validators.required }),
      imgURL: new FormControl(null, { validators: Validators.required }),
      besoinGameMaster: new FormControl(false),
    });
    this.tableForm = new FormGroup({
      nomTable: new FormControl(null, { validators: Validators.required }),
      capacite: new FormControl(null, { validators: Validators.required }),
      imgUrl: new FormControl(null, { validators: Validators.required }),
    });
  }

  private loadSection(section: string, service: any) {
    service.findAll().subscribe((data: any[]) => {
      this.data[section] = data;
      this.filteredItems[section] = [...data];

      if (section === 'reservations') {
        this.loadResaDuJour();
        this.loadTableJeuReservation();
      }
    });
  }

  filter(section: string, term: string) {
    term = term?.toLowerCase() || '';
    const statutFilter = this.statutControls[section]?.value || '';

    /*this.filteredItems[section] = this.data[section].filter((item) => {
      const matchesTerm = (
        item.nom ||
        item.name ||
        item.nomTable ||
        item.nomBadge ||
        item.client.nom ||
        item.client.prenom ||
        item.titre ||
        ''
      )
        .toLowerCase()
        .includes(term);
      const matchesStatut = statutFilter
        ? (item.statutReservation || item.statutLocation || item.note)?.toLowerCase() ===
          statutFilter.toLowerCase()
        : true;
      return matchesTerm && matchesStatut;
    });*/
    this.filteredItems[section] = this.data[section].filter((item) => {
      // Recherche textuelle sur les champs disponibles
      const searchableText = [item.nom, item.name, item.nomTable, item.nomBadge, item.titre]
        .filter((v) => !!v)
        .join(' ')
        .toLowerCase();

      const matchesTerm = searchableText.includes(term);

      // ----------- Gestion du filtre 'statut' ou 'note' selon section -----------
      let matchesStatut = true;

      if (section === 'avis' && statutFilter !== '') {
        matchesStatut = item.note == statutFilter; // filtre sur la note
      } else if (section === 'reservations' && statutFilter !== '') {
        matchesStatut = item.statutReservation?.toLowerCase() === statutFilter.toLowerCase();
      } else if (section === 'emprunts' && statutFilter !== '') {
        matchesStatut = item.statutLocation?.toLowerCase() === statutFilter.toLowerCase();
      }

      return matchesTerm && matchesStatut;
    });
    if (this.lastSort[section]) {
      this.sortData(section, this.lastSort[section]);
    }
  }
  sortData(section: string, sort: Sort) {
    this.lastSort[section] = sort;
    const data = this.filteredItems[section].slice();
    if (!sort.active || sort.direction === '') {
      this.filteredItems[section] = data;
      return;
    }

    this.filteredItems[section] = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      const valueA = a[sort.active];
      const valueB = b[sort.active];

      if (valueA == null) return 1;
      if (valueB == null) return -1;

      return (valueA < valueB ? -1 : 1) * (isAsc ? 1 : -1);
    });
  }

  ajouter(section: string) {
    switch (section) {
      case 'jeux':
        this.jeuForm.reset();
        break;
      case 'employes':
        this.employeForm.reset();
        break;
      case 'tables':
        this.tableForm.reset();
        break;
      case 'badges':
        this.badgeForm.reset();
        break;
    }

    this.currentEdit[section] = null;
    this.editMode = true;
  }
  modifier(section: string, item: any) {
    console.log('modifier called', section, item);
    this.currentEdit[section] = item;
    this.editMode = true;

    if (section === 'jeux') {
      // Pré-remplir le formulaire
      this.jeuForm.patchValue({
        nom: item.nom,
        typesJeux: item.typesJeux,
        ageMinimum: item.ageMinimum,
        nbJoueurMinimum: item.nbJoueurMinimum,
        nbJoueurMaximum: item.nbJoueurMaximum,
        duree: item.duree,
        nbExemplaire: item.nbExemplaire,
        note: item.note,
        categoriesJeux: item.categoriesJeux,
        imgURL: item.imgURL,
        besoinGameMaster: item.besoinGameMaster,
      });
    } else if (section === 'tables') {
      // Pré-remplir le formulaire
      this.tableForm.patchValue({
        nomTable: item.nomTable,
        capacite: item.capacite,
        imgUrl: item.imgUrl,
      });
    } else if (section === 'badges') {
      // Pré-remplir le formulaire
      this.badgeForm.patchValue({
        imgURL: item.imgURL,
        pointMin: item.pointMin,
        nomBadge: item.nomBadge,
      });
    } else if (section === 'employes') {
      // Pré-remplir le formulaire
      this.employeForm.patchValue({
        nom: item.nom,
        prenom: item.prenom,
        mail: item.mail,
        telephone: item.telephone,
        job: item.job,
        gameMaster: item.gameMaster,
      });
    } else if (section === 'reservations') {
      // Pré-remplir le formulaire
      this.reservationForm.patchValue({
        statutReservation: item.statutReservation,
      });
    } else if (section === 'emprunts') {
      this.empruntForm.patchValue({
        statutEmprunt: item.statutLocation,
      });
    }
  }
  supprimer(section: string, item: any) {
    if (!item?.id) return;
    const confirmation = window.confirm(`Voulez-vous vraiment supprimer cet élément ?`);
    if (!confirmation) return;
    switch (section) {
      case 'jeux':
        this.jeuService.deleteById(item.id);
        break;
      case 'employes':
        this.employeService.deleteById(item.id);
        break;
      case 'tables':
        this.tableJeuService.deleteById(item.id);
        break;
      case 'badges':
        this.badgeService.deleteById(item.id);
        break;
      case 'reservations':
        this.reservationService.deleteById(item.id);
        break;
      case 'emprunts':
        this.empruntService.deleteById(item.id);
        break;
    }
  }
  /*private removeFromData(section: string, id: number) {
    this.data[section] = this.data[section].filter((i) => i.id !== id);
    this.filteredItems[section] = this.filteredItems[section].filter((i) => i.id !== id);
  }*/

  sauvergarder(section: string, item: any) {
    switch (section) {
      case 'jeu':
        const formDataJeu = this.jeuForm.getRawValue();
        const jeuId = this.currentEdit['jeux'] ? this.currentEdit['jeux'].id : 0;
        const jeuModifiee = new Jeu(
          jeuId, // si null, id sera undefined => création
          formDataJeu.nom,
          formDataJeu.typesJeux,
          formDataJeu.ageMinimum,
          formDataJeu.nbJoueurMinimum,
          formDataJeu.nbJoueurMaximum,
          formDataJeu.duree,
          formDataJeu.nbExemplaire,
          formDataJeu.note,
          formDataJeu.categoriesJeux,
          formDataJeu.imgURL,
          formDataJeu.besoinGameMaster ?? false
        );
        console.log(jeuModifiee.toJson());
        console.log(jeuModifiee);
        this.jeuService.save(jeuModifiee);
        break;
      case 'employe':
        const formDataEmploye = this.employeForm.getRawValue();
        const employeId = this.currentEdit['employes'] ? this.currentEdit['employes'].id : 0;
        const mdp = formDataEmploye.mdp || '123456';
        const employeModifiee = new Employe(
          employeId, // si null, id sera undefined => création
          formDataEmploye.nom,
          formDataEmploye.prenom,
          formDataEmploye.mail,
          mdp,
          formDataEmploye.telephone,
          formDataEmploye.job,
          formDataEmploye.gameMaster ?? false
        );
        console.log(employeModifiee.toJson());
        console.log(employeModifiee);
        //console.log(mdp)
        this.employeService.save(employeModifiee);
        break;
      case 'table':
        const formDataTable = this.tableForm.getRawValue();
        const tableId = this.currentEdit['tables'] ? this.currentEdit['tables'].id : 0;
        const tableModifiee = new TableJeu(
          tableId, // si null, id sera undefined => création
          formDataTable.nomTable,
          formDataTable.capacite,
          formDataTable.imgUrl
        );
        console.log(tableModifiee.toJson());
        console.log(tableModifiee);
        this.tableJeuService.save(tableModifiee);
        break;
      case 'badge':
        const formDataBadge = this.badgeForm.getRawValue();
        const badgeId = this.currentEdit['badges'] ? this.currentEdit['badges'].id : 0;
        const badgeModifiee = new Badge(
          badgeId, // si null, id sera undefined => création
          formDataBadge.nomBadge,
          formDataBadge.pointMin,
          formDataBadge.imgURL
        );
        console.log(badgeModifiee.toJson());
        console.log(badgeModifiee);
        this.badgeService.save(badgeModifiee);
        break;
      case 'reservation':
        const formDataReservation = this.reservationForm.getRawValue();
        const reservationModifiee = new Reservation(
          this.currentEdit['reservations'].id,
          new Date(this.currentEdit['reservations'].datetimeDebut),
          new Date(this.currentEdit['reservations'].datetimeFin),
          this.currentEdit['reservations'].nbJoueur,
          this.currentEdit['reservations'].tableJeu,
          this.currentEdit['reservations'].jeu,
          formDataReservation.statutReservation, // nouveau statut
          this.currentEdit['reservations'].client,
          this.currentEdit['reservations'].gameMaster
        );
        console.log(reservationModifiee.toJson());
        this.reservationService.save(reservationModifiee);
        break;
      case 'emprunt':
        const formEmprunt = this.empruntForm.getRawValue();
        const empruntModifie = new Emprunt(
          this.currentEdit['emprunts'].id,
          this.currentEdit['emprunts'].dateEmprunt,
          this.currentEdit['emprunts'].dateRetour,
          formEmprunt.statutEmprunt,
          this.currentEdit['emprunts'].client,
          this.currentEdit['emprunts'].jeu,
          formEmprunt.dateRetourReel
        );
        console.log(empruntModifie.toJson());
        this.empruntService.save(empruntModifie).subscribe({
          next: (res) => {
            console.log('Emprunt sauvegardé', res);
            this.loadSection('emprunts', this.empruntService);
            this.editMode = false;
            this.currentEdit['emprunts'] = null;
          },
          error: (err) => console.error('Erreur lors de la sauvegarde de l’emprunt', err),
        });
        break;
    }

    this.currentEdit[section] = null;
    this.editMode = false;
  }

  loadResaDuJour() {
    const today = new Date();
    this.reservationsDuJour = this.data['reservations'].filter((r: any) => {
      const dateDebut = new Date(r.datetimeDebut);
      return (
        dateDebut.getFullYear() === today.getFullYear() &&
        dateDebut.getMonth() === today.getMonth() &&
        dateDebut.getDate() === today.getDate()
      );
    }).length;
  }

  loadTableJeuReservation() {
    this.filteredItems['reservations'].forEach((res: Reservation) => {
      this.tableJeuService.findById(res.tableID as number).subscribe((table) => {
        res.tableJeu = table;
      });
    });
  }
}
