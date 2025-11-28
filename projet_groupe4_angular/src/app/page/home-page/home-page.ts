import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { RouterLink } from '@angular/router';
import { Badge } from '../../dto/badge';
import { BadgeService } from '../../service/badge-service';
import { ClientService } from '../../service/client-service';
import { Reservation } from '../../dto/reservation';
import { Avis } from '../../dto/avis';
import { Emprunt } from '../../dto/emprunt';
import { ReservationService } from '../../service/reservation-service';
import { EmpruntService } from '../../service/emprunt-service';
import { AvisService } from '../../service/avis-service';
import { JeuService } from '../../service/jeu-service';

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {
  public nom: string = '';
  public prenom: string = '';
  public pointFidelite: number = 0;
  public badgeActuel?: Badge;
  public role: string = '';

  reservations: Reservation[] = [];
  emprunts: Emprunt[] = [];
  avisList: Avis[] = [];

  topReserves: any[] = [];
  topLoc: any[] = [];
  topLike: any[] = [];

  constructor(
    private authService: AuthService,
    private badgeService: BadgeService,
    private clientService: ClientService,
    private reservationService: ReservationService,
    private empruntService: EmpruntService,
    private jeuService: JeuService
  ) {}

  ngOnInit(): void {
    //this.nom = this.authService.nom;
    //this.prenom = this.authService.prenom;
    this.role = this.authService.role;
    const username = this.authService.username;

    this.clientService.findByUsername(username).subscribe((client) => {
      this.nom = client.nom;
      this.prenom = client.prenom;
      this.pointFidelite = client.pointFidelite;

      this.badgeService.findAll().subscribe((badges) => {
        this.badgeActuel = this.getBadgeForPoints(this.pointFidelite, badges);
      });

      



    });

    this.reservationService.findAll().subscribe((res) => {
        this.reservations = res;
        this.calculateTopReserves();
      });

      this.empruntService.findAll().subscribe((emp) => {
        this.emprunts = emp;
        this.calculateTopLoc();
      });

      this.loadTopLike();
  }

  public getBadgeForPoints(points: number, badges: Badge[]): Badge {
    return badges.filter((b) => b.pointMin <= points).sort((a, b) => b.pointMin - a.pointMin)[0];
  }

  calculateTopReserves() {
    const counts: Record<number, { jeu: any; count: number }> = {};

    this.reservations.forEach((r) => {
      const j = r.jeu;
      const jeuId = j.id;

      if (!counts[jeuId]) {
        counts[jeuId] = { jeu: j, count: 0 };
      }
      counts[jeuId].count++;
    });

    // Transformer en tableau, trier et prendre le top 3
    this.topReserves = Object.values(counts)
      .sort((a, b) => b.count - a.count)
      .slice(0, 3);
  }

  calculateTopLoc(){

    const counts: Record<number, { jeu: any; count: number }> = {};

    this.emprunts.forEach((e) => {
      const j = e.jeu;
      const jeuId = j.id;

      if (!counts[jeuId]) {
        counts[jeuId] = { jeu: j, count: 0 };
      }
      counts[jeuId].count++;
    });

    // Transformer en tableau, trier et prendre le top 3
    this.topLoc = Object.values(counts)
      .sort((a, b) => b.count - a.count)
      .slice(0, 3);



  }

  loadTopLike(){
    this.jeuService.findAll().subscribe((jeux) => {
    this.topLike = jeux
      .sort((a, b) => b.note - a.note) // tri décroissant par note
      .slice(0, 3) // top 3
      .map(j => ({ jeu: j, count: j.note })); 
  });
  }



}
