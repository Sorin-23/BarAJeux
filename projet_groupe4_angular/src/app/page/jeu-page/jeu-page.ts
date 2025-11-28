import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CarouselModule } from 'primeng/carousel';
import { TagModule } from 'primeng/tag';
import { Jeu } from '../../dto/jeu';
import { JeuService } from '../../service/jeu-service';

@Component({
  selector: 'app-jeu-page',
  imports: [CommonModule,
    CarouselModule,
    ButtonModule,
    TagModule,
    HttpClientModule,
    ReactiveFormsModule],
  templateUrl: './jeu-page.html',
  styleUrl: './jeu-page.css',
})
export class JeuPage implements OnInit {
  jeux: Jeu[] = [];
  carouselIndex = 0;
  filtreForm: FormGroup;

  constructor(private jeuService: JeuService) {
    this.filtreForm = new FormGroup({
      nbPersonnes: new FormControl(null),
      ageMin: new FormControl(null),
      dureeMax: new FormControl(null)
    });
  }

  ngOnInit(): void {
    this.jeuService.findAll().subscribe((data) => {
      this.jeux = data;
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
    this.jeuService.findAll().subscribe((data) => {
      this.jeux = data.filter((jeu) => {
        let ok = true;

        if (filtre.nbPersonnes) {
          ok = ok && jeu.nbJoueurMinimum <= filtre.nbPersonnes && jeu.nbJoueurMaximum >= filtre.nbPersonnes;
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
}
