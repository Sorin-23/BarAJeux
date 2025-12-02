import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { map } from 'rxjs';

import { ClientService } from '../../service/client-service';
import { Client } from '../../dto/client';
import { Reservation } from '../../dto/reservation';
import { ClientWithReservationResponse } from '../../dto/client-with-reservation-response';
import { Router, RouterModule } from '@angular/router';

interface ReservationDisplay {
  original: Reservation;
  titreAvis: string;
  commentaire: string;
  note: number;
  avisModifiable: boolean;
}

@Component({
  selector: 'app-client-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './client-page.html',
  styleUrl: './client-page.css',
})
export class ClientPage implements OnInit {
  currentView: 'info' | 'reservation' = 'info';
  isEditing: boolean = false;

  // Initialize empty to avoid crashes on load
  client: Client;
  clientBackup: Client | null = null;
  reservationsList: ReservationDisplay[] = [];
  clientWithResa: ClientWithReservationResponse | null = null;

  constructor(private clientService: ClientService, private router: Router) {
    // Ensure createEmptyClient exists in your Service, or initialize manually
    this.client = this.clientService.createEmptyClient();
  }

  ngOnInit(): void {
    // Check LocalStorage first (where Login Page saved it)
    const username = localStorage.getItem('username');

    if (username) {
      this.loadClientData(username);
    } else {
      console.warn('No user logged in. Please log in.');
    }
  }

  loadClientData(username: string) {
    this.clientService.findByUsername(username).subscribe({
      next: (clientObj) => {
        this.client = clientObj;

        // Load reservations only if we have a valid ID
        if (this.client.id) {
          this.loadReservations(this.client.id);
        }
      },
      error: (err) => console.error('Error loading client:', err),
    });
  }

  loadReservations(clientId: number) {
    this.clientService
      .getReservations(clientId)
      .pipe(
        map((data: any) => {
          const list = data.reservations ?? []; // extraction du tableau

          return list.map(
            (item: any) =>
              ({
                original: item,
                titreAvis: '',
                commentaire: '',
                note: 0,
                avisModifiable: true,
              } as ReservationDisplay)
          );
        })
      )
      .subscribe({
        next: (list) => (this.reservationsList = list),
        error: (err) => console.error('Error loading reservations', err),
      });
  }

  switchView(view: 'info' | 'reservation') {
    this.currentView = view;
  }

  enableEdit() {
    // Deep copy using JSON to avoid reference issues
    const jsonCopy = this.client.toJson();

    this.clientBackup = new Client(
      this.client.id,
      this.client.nom,
      this.client.prenom,
      this.client.mail,
      this.client.mdp,
      this.client.telephone,
      this.client.pointFidelite,
      new Date(this.client.dateCreation),
      new Date(this.client.dateLastConnexion),
      [...this.client.reservations],
      [...this.client.emprunts],
      this.client.ville,
      this.client.codePostale,
      this.client.adresse
    );
    this.isEditing = true;
  }

  cancelEdit() {
    if (this.clientBackup) {
      this.client = this.clientBackup;
    }
    this.isEditing = false;
  }

  saveProfile() {
    console.log('Saving profile for ID:', this.client.id);
    this.clientService.save(this.client);
    this.isEditing = false;
    alert('Modifications envoyées !');
  }

  // --- AVIS ---
  setRating(item: ReservationDisplay, starValue: number) {
    if (item.avisModifiable) item.note = starValue;
  }

  saveAvis(item: ReservationDisplay) {
    if (!item.titreAvis || !item.commentaire || item.note === 0) {
      alert('Veuillez tout remplir.');
      return;
    }
    const payload = {
      titre: item.titreAvis,
      commentaire: item.commentaire,
      note: item.note,
    };
    // Ensure item.original has an 'id'
    this.clientService.saveAvis(item.original.id, payload).subscribe({
      next: () => {
        alert('Avis publié !');
        item.avisModifiable = false;
      },
    });
  }

  getBadgeLevel(points: number): string {
    if (!points) return 'novice';
    if (points >= 500) return 'maitre';
    if (points >= 200) return 'expert';
    if (points >= 100) return 'apprenti';
    return 'novice';
  }

  goToReservations() {
    if (this.client?.id) {
      this.router.navigate(['/reservations', this.client.id]);
    }
  }
}
