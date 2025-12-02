import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

// Make sure these paths match your actual file names (dash vs dot)
import { AuthService } from '../../service/auth-service'; 
import { BadgeService } from '../../service/badge-service';
import { ClientService } from '../../service/client-service';
import { ReservationService } from '../../service/reservation-service';
import { EmpruntService } from '../../service/emprunt-service';
import { JeuService } from '../../service/jeu-service';
import { Top } from '../../dto/top';
import { TopService } from '../../service/top-service';

import { Badge } from '../../dto/badge';
import { Reservation } from '../../dto/reservation';
import { Avis } from '../../dto/avis';
import { Emprunt } from '../../dto/emprunt';

@Component({
  selector: 'app-home-page',
  standalone: true, // Assuming standalone based on previous code
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

    if (username) {
      this.clientService.findByUsername(username).subscribe({
        next: (client) => {
          this.nom = client.nom;
          this.prenom = client.prenom;
          this.pointFidelite = client.pointFidelite; // Ensure this matches DTO (pointFidelite vs point_fidelite)

          // Only load badge if we have points
          this.badgeService.findAll().subscribe((badges) => {
            this.badgeActuel = this.getBadgeForPoints(this.pointFidelite, badges);
          });
        },
        error: (err) => console.error('Error loading client:', err)
      });
    };

    this.topService.getTopReservations().subscribe((res) => (this.topReserves = res));

    this.topService.getTopEmprunts().subscribe((res) => (this.topLoc = res));

    this.topService.getTopNotes().subscribe((res) => (this.topLike = res));
  }

  public getBadgeForPoints(points: number, badges: Badge[]): Badge {
    // Safety check if badges is empty
    if (!badges || badges.length === 0) return {} as Badge;
    
    const sorted = badges.filter((b) => b.pointMin <= points).sort((a, b) => b.pointMin - a.pointMin);
    return sorted.length > 0 ? sorted[0] : badges[0]; // Return the first badge if none match
  }
}