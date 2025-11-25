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
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {  MatSortModule, Sort } from '@angular/material/sort';



@Component({
  selector: 'app-admin-page',
  imports: [CommonModule, ReactiveFormsModule, MatSortModule],
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
  };


  filteredItems: { [key: string]: any[] } = {
    jeux: [],
    employes: [],
    tables: [],
    badges: [],
    reservations: [],
  };
  lastSort: { [key: string]: Sort | null } = {};


  searchControls: { [key: string]: FormControl } = {
    jeux: new FormControl(''),
    employes: new FormControl(''),
    tables: new FormControl(''),
    badges: new FormControl(''),
    reservations: new FormControl('')
  };

  currentEdit: { [key: string]: any } = {
    jeu: null,
    employe: null,
    table: null,
    badge: null,
    reservation: null,
  };

  currentSection = 'dashboard';

  constructor(
    private jeuService: JeuService,
    private employeService: EmployeService,
    private tableJeuService: TableJeuService,
    private badgeService: BadgeService,
    private reservationService: ReservationService,
  ) {}

    ngOnInit(): void {
  
    this.loadSection('jeux', this.jeuService);
    this.loadSection('employes', this.employeService);
    this.loadSection('tables', this.tableJeuService);
    this.loadSection('badges', this.badgeService);
    this.loadSection('reservations', this.reservationService);


    Object.keys(this.searchControls).forEach(section => {
      this.searchControls[section].valueChanges.subscribe(term => {
        this.filter(section, term);
      });
    });
}

private loadSection(section: string, service: any) {
    service.findAll().subscribe((data: any[]) => {
      this.data[section] = data;
      this.filteredItems[section] = [...data];
    });
  }



filter(section: string, term: string) {
    term = term?.toLowerCase() || '';
    if (!term) {
      this.filteredItems[section] = [...this.data[section]];
    } else {
      this.filteredItems[section] = this.data[section].filter(item =>
        (item.nom || item.name || '').toLowerCase().includes(term)
      );
    if(this.lastSort[section]){
      this.sortData(section, this.lastSort[section]);
    }
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
    this.openModal(section);
  }
modifier(section: string, item: any) {
    this.currentEdit[section] = item;
    this.openModal(section);
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
    }
  }
  private removeFromData(section: string, id: number) {
    this.data[section] = this.data[section].filter(i => i.id !== id);
    this.filteredItems[section] = this.filteredItems[section].filter(i => i.id !== id);
  }



  openModal(section : string) {
    //ouverture du modal en automatique 
  }


  
}  