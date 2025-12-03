import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { TagModule } from 'primeng/tag';
import { StatutLocation } from '../../dto/enum/statut-location';
import { Jeu } from '../../dto/jeu';
import { AuthService } from '../../service/auth-service';
import { ClientService } from '../../service/client-service';
import { EmpruntService } from '../../service/emprunt-service';
import { JeuService } from '../../service/jeu-service';
import { Emprunt } from '../../dto/emprunt';

@Component({
  selector: 'app-jeu-page',
  imports: [
    CommonModule,
    CarouselModule,
    ButtonModule,
    TagModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  templateUrl: './jeu-page.html',
  styleUrl: './jeu-page.css',
})
export class JeuPage implements OnInit {
  jeux: Jeu[] = [];
  carouselIndex = 0;
  filtreForm: FormGroup;
  jeuSelectionne?: Jeu;
  formEmprunt: FormGroup;
  clientId?: any;
  formEmpruntVisible = false;
  today: string = new Date().toISOString().substring(0, 10);

  constructor(
    private jeuService: JeuService,
    private authService: AuthService,
    private clientService: ClientService,
    private empruntService: EmpruntService
  ) {
    this.filtreForm = new FormGroup({
      nbPersonnes: new FormControl(null),
      ageMin: new FormControl(null),
      dureeMax: new FormControl(null),
      dateEmprunt: new FormControl(null),
    });
    this.formEmprunt = new FormGroup({
      jeu: new FormControl({ value: null, disabled: true }),
      client: new FormControl({ value: null, disabled: true }),
      dateEmprunt: new FormControl(null, { validators: Validators.required }),
      dateRetour: new FormControl(null, { validators: Validators.required }),
    });
  }

  ngOnInit(): void {
    this.jeuService.findAll().subscribe((data) => {
      this.jeux = data;
    });
    const username = this.authService.username;

    this.clientService.findByUsername(username).subscribe((client) => {
      this.clientId = client.id;
    });
  }
  prev(): void {
    if (this.carouselIndex > 0) {
      this.carouselIndex--;
    }
    this.scrollCarousel();
  }

  next(): void {
    if (this.carouselIndex < this.jeux.length - 1) {
      this.carouselIndex++;
    }
    this.scrollCarousel();
  }

  scrollCarousel(): void {
    const carousel = document.querySelector('.carrousel') as HTMLElement;
    const cardWidth = carousel.children[0]?.clientWidth || 0;
    carousel.scrollLeft = this.carouselIndex * (cardWidth + 20); // 20 = gap
  }

  appliquerFiltre() {
    const filtre = this.filtreForm.value;
    if (!filtre.dateEmprunt) {
      alert("Veuillez sélectionner une date d'emprunt avant d'appliquer le filtre.");
      return;
    }

    this.jeuService.findDisponibles(filtre.dateEmprunt).subscribe((data) => {
      this.jeux = data.filter((jeu) => {
        let ok = true;

        if (filtre.nbPersonnes) {
          ok =
            ok &&
            jeu.nbJoueurMinimum <= filtre.nbPersonnes &&
            jeu.nbJoueurMaximum >= filtre.nbPersonnes;
        }
        if (filtre.ageMin) {
          ok = ok && jeu.ageMinimum >= filtre.ageMin;
        }
        if (filtre.dureeMax) {
          ok = ok && jeu.duree <= filtre.dureeMax;
        }

        return ok;
      });

      // reset carousel après filtrage
      this.carouselIndex = 0;
      this.scrollCarousel();
    });
  }
  calculerDateRetour(dateDebut: Date): Date {
    const DUREE_EMPRUNT_STANDARD = 15;

    let retour = new Date(dateDebut);

    // ajouter 15 jours
    retour.setDate(retour.getDate() + DUREE_EMPRUNT_STANDARD);

    // si dimanche → repousser au lundi
    if (retour.getDay() === 0) {
      // 0 = Dimanche
      retour.setDate(retour.getDate() + 1);
    }

    return retour;
  }

  choisirJeu(jeu: Jeu) {
    const dateDebut = this.filtreForm.value.dateEmprunt;
    if (!dateDebut) {
      alert(
        'Veuillez sélectionner une date d’emprunt avant de choisir un jeu et appliquer le filtre.'
      );
      return; // arrêter l’exécution
    }
    this.jeuSelectionne = jeu;

    const clientId = this.clientId;
    const dateRetour = this.calculerDateRetour(dateDebut).toISOString().substring(0, 10);

    console.log(this.jeuSelectionne);

    this.formEmprunt.patchValue({
      jeu: jeu.nom,
      client: clientId,
      dateEmprunt: dateDebut,
      dateRetour: dateRetour,
      StatutLocation: StatutLocation.EN_COURS,
    });
    this.formEmprunt.get('dateEmprunt')?.disable();
    this.formEmprunt.get('dateRetour')?.disable();
    this.formEmpruntVisible = true;
  }

  validerEmprunt() {
    if (this.formEmprunt.invalid) return;

    const empruntData = this.formEmprunt.getRawValue();

    const nouveauEmprunt = new Emprunt(
      0, // _id = 0 pour création
      empruntData.dateEmprunt, // _dateEmprunt
      empruntData.dateRetour, // _dateRetour
      StatutLocation.EN_COURS, // _statutLocation
      { id: this.clientId } as any, // _client minimal
      this.jeuSelectionne as any, // _jeu minimal
      undefined // _dateRetourReel
    );

    this.empruntService.save(nouveauEmprunt);

    alert('Emprunt créé avec succès !');
    this.formEmpruntVisible = false;
    this.jeuSelectionne = undefined;
    this.filtreForm.reset();
  }
}
