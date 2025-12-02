import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../service/client-service';
import { Reservation } from '../../dto/reservation';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Avis } from '../../dto/avis';
import { AvisService } from '../../service/avis-service';
import { TableJeuService } from '../../service/table-jeu-service';

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
    private router: Router,
    private avisService : AvisService ,
    private tableJeuService : TableJeuService
  ) {}

  ngOnInit(): void {

    const clientId = Number(this.route.snapshot.paramMap.get('id'));
    if (clientId) {
      this.clientService.getReservations(clientId).subscribe({
        next: (data) => {
          this.reservations = data.reservations.map((r: Reservation) => {
            // Appel pour récupérer l'avis existant
            this.avisService.getAvisByReservation(r.id).subscribe({
              next: (avis) => {
                if (avis) {
                  avis.note = Number(avis.note);
                  r.avis = avis;
                  console.log(avis)
                  r.avisModifiable = false; // pas modifiable, déjà existant
                } else {
                  r.avis = new Avis(0, 0, '', '', r.id);
                  r.avisModifiable = true; // peut créer un avis
                }
              },
              
            });
            if (r.tableID) {
            this.tableJeuService.findById(r.tableID).subscribe({
              next: (table) => r.tableJeu = table,
              error: (err) => console.error(`Erreur récupération table réservation ${r.id}:`, err)
            });
          }
            
            
            return r;
          });
        },
      });
    };

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
    const payload = r.avis!.toJson();
    console.log('Payload envoyé au backend :', payload);

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
