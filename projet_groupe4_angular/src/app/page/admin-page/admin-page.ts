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
  };

  filteredItems: { [key: string]: any[] } = {
    jeux: [],
    employes: [],
    tables: [],
    badges: [],
    reservations: [],
    emprunts: [],
  };
  lastSort: { [key: string]: Sort | null } = {};

  searchControls: { [key: string]: FormControl } = {
    jeux: new FormControl(''),
    employes: new FormControl(''),
    tables: new FormControl(''),
    badges: new FormControl(''),
    reservations: new FormControl(''),
    emprunts: new FormControl(''),
  };

  currentEdit: { [key: string]: any } = {
    jeu: null,
    employe: null,
    table: null,
    badge: null,
    reservation: null,
    emprunt: null,
  };

  statutControls: { [key: string]: FormControl } = {
    reservations: new FormControl(''),
    emprunts: new FormControl(''),
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

  constructor(
    private jeuService: JeuService,
    private employeService: EmployeService,
    private tableJeuService: TableJeuService,
    private badgeService: BadgeService,
    private reservationService: ReservationService,
    private empruntService: EmpruntService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadSection('jeux', this.jeuService);
    this.loadSection('employes', this.employeService);
    this.loadSection('tables', this.tableJeuService);
    this.loadSection('badges', this.badgeService);
    this.loadSection('reservations', this.reservationService);
    this.loadSection('emprunts', this.empruntService);


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

    this.filteredItems[section] = this.data[section].filter((item) => {
      const matchesTerm = (
        item.nom ||
        item.name ||
        item.nomTable ||
        item.nomBadge ||
        item.client.nom ||
        item.client.prenom ||
        ''
      )
        .toLowerCase()
        .includes(term);
      const matchesStatut = statutFilter
        ? (item.statutReservation || item.statutLocation)?.toLowerCase() ===
          statutFilter.toLowerCase()
        : true;
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
    this.currentEdit[section] = null;
    this.editMode = true;
  }
  modifier(section: string, item: any) {
    console.log('modifier called', section, item);
    this.currentEdit[section] = item;
    this.editMode = true;
    if (section === 'reservations') {
      // Pré-remplir le formulaire
      this.reservationForm.patchValue({
        statutReservation: item.statutReservation,
      });
    }
  }
  supprimer(section: string, item: any) {
    if (!item?.id) return;
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
  private removeFromData(section: string, id: number) {
    this.data[section] = this.data[section].filter((i) => i.id !== id);
    this.filteredItems[section] = this.filteredItems[section].filter((i) => i.id !== id);
  }

  sauvergarder(section: string, item: any) {
    console.log('sauvegarder called', section, item, this.reservationForm.value);
    switch (section) {
      case 'jeu':
        this.jeuService.save(item.id);
        break;
      case 'employe':
        this.employeService.save(item.id);
        break;
      case 'table':
        this.tableJeuService.save(item.id);
        break;
      case 'badge':
        this.badgeService.save(item.id);
        break;
      case 'reservation':
        const formData = this.reservationForm.getRawValue();
        const reservationModifiee = new Reservation(
          this.currentEdit['reservations'].id,
          new Date(this.currentEdit['reservations'].datetimeDebut),
          new Date(this.currentEdit['reservations'].datetimeFin),
          this.currentEdit['reservations'].nbJoueur,
          this.currentEdit['reservations'].tableJeu,
          this.currentEdit['reservations'].jeu,
          formData.statutReservation, // nouveau statut
          this.currentEdit['reservations'].client,
          this.currentEdit['reservations'].gameMaster
        );
        console.log(reservationModifiee.toJson());
        this.reservationService.save(reservationModifiee);
        break;
      case 'emprunt':
        this.empruntService.save(item.id);
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
