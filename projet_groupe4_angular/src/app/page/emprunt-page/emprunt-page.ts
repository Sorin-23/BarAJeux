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
          this.emprunts.forEach((e) => {
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
}
