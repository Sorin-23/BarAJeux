import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { map } from 'rxjs';
import { ClientService } from '../../service/client-service';
import { Client } from '../../dto/client';
import { Reservation } from '../../dto/reservation';
import { ClientWithReservationResponse } from '../../dto/client-with-reservation-response';
import { Router, RouterModule } from '@angular/router';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-client-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  templateUrl: './client-page.html',
  styleUrls: ['./client-page.css'],
})
export class ClientPage implements OnInit {
  currentView: 'info' | 'reservation' = 'info';
  isEditing: boolean = false;
  isChangingPassword: boolean = false;

  client: Client;
  clientBackup: Client | null = null;
  reservationsList: Reservation[] = [];
  clientWithResa: ClientWithReservationResponse | null = null;

  oldPassword: string = '';
  newPassword: string = '';
  confirmPassword: string = '';
  passwordError: string = '';
  passwordSuccess: string = '';

  passwordForm: FormGroup;

  constructor(private clientService: ClientService, private router: Router) {
    this.client = this.clientService.createEmptyClient();
    this.passwordForm = new FormGroup(
      {
        oldPassword: new FormControl('', Validators.required),
        newPassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
        confirmPassword: new FormControl('', Validators.required),
      },
      { validators: this.passwordMatchValidator }
    );
  }

  ngOnInit(): void {
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
          const list = data.reservations ?? [];
          return list.map(
            (item: any) =>
              ({
                original: item,
                titreAvis: '',
                commentaire: '',
                note: 0,
                avisModifiable: true,
              } as unknown as Reservation)
          ); // Type assertion to `Reservation`
        })
      )
      .subscribe({
        next: (list) => (this.reservationsList = list),
        error: (err) => console.error('Error loading reservations', err),
      });
  }

  passwordMatchValidator(group: AbstractControl): { [key: string]: boolean } | null {
    const password = group.get('newPassword')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return password === confirm ? null : { passwordMismatch: true };
  }

  changePassword(): void {
    if (this.passwordForm.invalid) {
      this.passwordError = 'Veuillez corriger le formulaire.';
      return;
    }

    const oldPassword = this.passwordForm.value.oldPassword.trim();
    const newPassword = this.passwordForm.value.newPassword.trim();

    this.clientService.changePassword(this.client.id, oldPassword, newPassword).subscribe({
      next: (response) => {
        this.passwordSuccess = 'Mot de passe mis à jour';
        this.passwordError = '';
        this.passwordForm.reset();
        this.isChangingPassword = false;
      },
      error: (err) => {
        if (err.status === 403) {
          this.passwordError = 'Ancien mot de passe incorrect';
        } else {
          this.passwordError = 'Erreur serveur.';
        }
      },
    });
  }

  toggleChangePasswordForm() {
    this.isChangingPassword = !this.isChangingPassword;
  }

  // --- Edit Client Profile ---
  enableEdit() {
    // Create a backup of the current client data
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
      // Restore the client data from the backup
      this.client = this.clientBackup;
    }
    this.isEditing = false;
  }

  saveProfile() {
    this.clientService.save(this.client);
    this.isEditing = false;
    alert('Modifications envoyées !');
  }

  // --- Navigation Methods ---
  goToReservations() {
    if (this.client?.id) {
      this.router.navigate(['/reservations', this.client.id]);
    }
  }

  // --- Badge Level Method ---
  getBadgeLevel(points: number): string {
    if (!points) return 'novice-transparent';
    if (points >= 500) return 'legende-transparent';
    if (points >= 300) return 'maitre-transparent';
    if (points >= 150) return 'expert-transparent';
    if (points >= 50) return 'apprenti-transparent';
    return 'novice-transparent';
  }
}
