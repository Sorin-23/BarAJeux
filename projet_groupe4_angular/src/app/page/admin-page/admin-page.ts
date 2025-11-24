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
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-admin-page',
  imports: [ReactiveFormsModule],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.css',
})
export class AdminPage implements OnInit {
  currentSection = 'dashboard';
  jeux: Jeu[] = [];
  employes: Employe[] = [];
  tables: TableJeu[] = [];
  badges: Badge[] = [];
  reservations: Reservation[] = [];

  jeuToEdit: Jeu | null = null;


  constructor(
    private jeuService: JeuService,
    private employeService: EmployeService,
    private tableJeuService: TableJeuService,
    private badgeService: BadgeService,
    private reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    this.jeuService.findAll().subscribe((data) => {
      this.jeux = data;
    });
    this.employeService.findAll().subscribe((data) => {
      this.employes = data;
    });
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data;
    });
    this.badgeService.findAll().subscribe((data) => {
      this.badges = data;
    });
    this.reservationService.findAll().subscribe((data) => {
      this.reservations = data;
    });
  }

  supprimerJeu(jeu:Jeu) {
    this.jeuService.deleteById(jeu.id);
  }
  modifierJeu(jeu:Jeu) {
    this.jeuService.findById(jeu.id).subscribe(jeu => {
    // stocke le jeu pour le modal
    this.jeuToEdit = jeu;
    this.openModal();
  });
    //ouvrir modal automatoque 
  }
  openModal() {
    //ouverture du modal en automatique 
  }

  
}
