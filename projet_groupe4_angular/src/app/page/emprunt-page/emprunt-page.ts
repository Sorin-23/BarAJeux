import { Component } from '@angular/core';
import { Emprunt } from '../../dto/emprunt';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../service/client-service';
import { AvisService } from '../../service/avis-service';
import { TableJeuService } from '../../service/table-jeu-service';
import { JeuService } from '../../service/jeu-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpruntService } from '../../service/emprunt-service';
import { StatutLocation } from '../../dto/enum/statut-location';

@Component({
  selector: 'app-emprunt-page',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './emprunt-page.html',
  styleUrl: './emprunt-page.css',
})
export class EmpruntPage {
  emprunts: Emprunt[] = [];

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService,
    private router: Router,
    private empruntService: EmpruntService,
    private jeuService: JeuService
  ) {}

  ngOnInit(): void {
    const clientId = Number(this.route.snapshot.paramMap.get('id'));
    if (clientId) {
      this.clientService.getEmprunts(clientId).subscribe({
        next: (data) => {
          this.emprunts = data.emprunts;
          // Appel pour récupérer l'avis existant
          this.emprunts.forEach((e) => {
            this.updateEmpruntStatus(e);
            if (e.jeu?.id) {
              this.jeuService.findById(e.jeu.id).subscribe({
                next: (jeu) => (e.jeu = jeu),
                error: (err) => console.error(`Erreur récupération jeu pour emprunt ${e.id}`, err),
              });
            }
          });
        },
        error: (err) => console.error('Erreur récupération client :', err),
      });
    }
  }

  isEmpruntHighlighted(emprunt: Emprunt): boolean {
    // Exemple: surligner les réservations aujourd'hui
    const today = new Date();
    const startDate = new Date(emprunt.dateEmprunt);
    return startDate.toDateString() === today.toDateString();
  }

  goBack() {
    this.router.navigate(['/client']);
  }

  /*setRating(emprunt: Emprunt, note: number) {
    if (emprunt.avisModifiable) emprunt.avis!.note = note;
  }

  saveAvis(e: Emprunt) {
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
    */

  terminerEmprunt(e: Emprunt) {
    e.dateRetourReel = new Date();

    // Préparer le payload pour le backend
    const payload = e.toJson();

    // Appel au service pour mettre à jour
    this.empruntService.save(payload).subscribe({
      next: (res) => {
        // Mettre à jour le tableau local
        const index = this.emprunts.findIndex((e) => e.id === res.id);
        if (index !== -1) {
          this.emprunts[index] = res;
        }
      },
      error: (err) => {
        console.error("Erreur lors de la mise à jour de l'emprunt :", err);
        alert("Impossible de terminer l'emprunt. Réessayez.");
      },
    });
  }

  updateEmpruntStatus(e: Emprunt) {
    const today = new Date();

    // Si le retour réel est déjà renseigné, on ne touche pas au statut
    if (e.dateRetourReel) {
      return;
    }

    const dateRetour = new Date(e.dateRetour);

    if (dateRetour < today) {
      e.statutLocation = StatutLocation.EN_RETARD;
    } else {
      e.statutLocation = StatutLocation.EN_COURS;
    }
  }
}
