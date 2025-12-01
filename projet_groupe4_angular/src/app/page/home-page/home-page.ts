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
import { Top } from '../../dto/top';
import { TopService } from '../../service/top-service';

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

  topReserves: Top[] = [];
  topLoc: Top[] = [];
  topLike: Top[] = [];

  constructor(
    private authService: AuthService,
    private badgeService: BadgeService,
    private clientService: ClientService,
    private reservationService: ReservationService,
    private empruntService: EmpruntService,
    private topService: TopService,
    private jeuService: JeuService
  ) {}

  ngOnInit(): void {
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

    this.topService.getTopReservations().subscribe((res) => (this.topReserves = res));

    this.topService.getTopEmprunts().subscribe((res) => (this.topLoc = res));

    this.topService.getTopNotes().subscribe((res) => (this.topLike = res));
  }

  public getBadgeForPoints(points: number, badges: Badge[]): Badge {
    return badges.filter((b) => b.pointMin <= points).sort((a, b) => b.pointMin - a.pointMin)[0];
  }
}
