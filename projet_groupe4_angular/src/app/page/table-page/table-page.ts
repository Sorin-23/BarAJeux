import { Component, OnInit } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TableJeuService } from '../../service/table-jeu-service';
import { TableJeu } from '../../dto/table-jeu';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-table-page',
  imports: [
    CommonModule,
    CarouselModule,
    ButtonModule,
    TagModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  templateUrl: './table-page.html',
  styleUrl: './table-page.css',
})
export class TablePage implements OnInit {
  tables: TableJeu[] = [];
  carouselIndex = 0;
  filtreForm: FormGroup;

  constructor(private tableJeuService: TableJeuService) {
    this.filtreForm = new FormGroup({
      nbPersonnes: new FormControl(null),
      date: new FormControl(null),
      heureArrivee: new FormControl(null),
      heureDepart: new FormControl(null),
    });
  }

  ngOnInit(): void {
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data;
    });
  }
  prev(): void {
    if (this.carouselIndex > 0) {
      this.carouselIndex--;
    }
    this.scrollCarousel();
  }

  next(): void {
    if (this.carouselIndex < this.tables.length - 1) {
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
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data.filter((table) => {
        let ok = true;
        if (filtre.nbPersonnes) {
          ok = ok && table.capacite >= filtre.nbPersonnes;
        }
        // ici on peut ajouter date et heure plus tard
        return ok;
      });
    });
  }
}
