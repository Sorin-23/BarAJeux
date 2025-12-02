import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../service/client-service';
import { Reservation } from '../../dto/reservation';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Avis } from '../../dto/avis';

@Component({
  selector: 'app-reservation-page',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './reservation-page.html',
  styleUrl: './reservation-page.css',
})
export class ReservationPage implements OnInit {
  reservations: Reservation[] = [];

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    const clientId = Number(this.route.snapshot.paramMap.get('id'));
    if (clientId) {
      this.clientService.getReservations(clientId).subscribe({
        next: (data) => {
          this.reservations = data.reservations.map((r: Reservation) => {
            // Crée un nouvel avis si inexistant
            if (!r.avis) {
              r.avis = new Avis(0, 0, '', '', r);
            }
            r.avisModifiable = true;
            return r;
          });
        },
        error: (err) => console.error('Erreur chargement réservations :', err),
      });
    }
  }

  goBack() {
    this.router.navigate(['/client']);
  }

  setRating(reservation: Reservation, note: number) {
    if (reservation.avisModifiable) reservation.avis!.note = note;
  }

  saveAvis(r: Reservation) {
    if (!r.avis!.titre || !r.avis!.commentaire || r.avis!.note === 0) {
      alert("Veuillez remplir tous les champs de l'avis et la note.");
      return;
    }

    // Créer le payload pour le backend
    const payload = r.avis;

    // Appel au service pour sauvegarder l'avis
    this.clientService.saveAvis(r.id, payload).subscribe({
      next: () => {
        alert('Avis publié !');
        // Désactiver l'édition et masquer le bouton
        r.avisModifiable = false;
      },
      error: (err) => {
        console.error("Erreur lors de l'enregistrement de l'avis", err);
        alert("Erreur lors de l'enregistrement de l'avis.");
      },
    });
  }
}
