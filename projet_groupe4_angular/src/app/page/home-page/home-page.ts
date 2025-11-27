import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { RouterLink } from '@angular/router';
import { Badge } from '../../dto/badge';
import { BadgeService } from '../../service/badge-service';
import { ClientService } from '../../service/client-service';

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
  public role: string ='';

  constructor(
    private authService: AuthService,
    private badgeService: BadgeService,
    private clientService: ClientService
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
}

  public getBadgeForPoints(points: number, badges: Badge[]): Badge {
    return badges.filter((b) => b.pointMin <= points).sort((a, b) => b.pointMin - a.pointMin)[0];
  }
}
