import { Component, OnInit } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TableJeuService } from '../../service/table-jeu-service';
import { TableJeu } from '../../dto/table-jeu';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Jeu } from '../../dto/jeu';
import { JeuService } from '../../service/jeu-service';
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
  tableChoisi = false;
  jeux: Jeu[] = [];
  tableSelectionne? : TableJeu;

  constructor(private tableJeuService: TableJeuService, private jeuService: JeuService) {
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

  appliquerFiltreTable() {
    const filtre = this.filtreForm.value;

    //convertir la date et heures du formulaire en type date-heure
    let filtreDebut: Date | null = null;
    let filtreFin: Date | null = null;

    if (filtre.date) {
      filtreDebut = new Date(filtre.date);
      if (filtre.heureArrivee) {
        const [hArrivee, mArrivee] = filtre.heureArrivee.split(':').map(Number);
        filtreDebut.setHours(hArrivee, mArrivee, 0, 0);
      }
      if (filtre.heureDepart) {
        filtreFin = new Date(filtre.date);
        const [hDepart, mDepart] = filtre.heureDepart.split(':').map(Number);
        filtreFin.setHours(hDepart, mDepart, 0, 0);
      }
    }
    this.tableJeuService.findAll().subscribe((data) => {
      this.tables = data.filter((table) => {
        let ok = true;
        if (filtre.nbPersonnes) {
          ok = ok && table.capacite >= filtre.nbPersonnes;
          console.log('=> rejetée pour nbPersonnes');
        }
        // ici on peut ajouter date et heure plus tard
        if (filtreDebut) {
          console.log('filtreDebut:', filtreDebut);
          const reservations = table.reservations || [];
          console.log('Table:', table.nomTable, 'reservations:', table._reservations);

          reservations.forEach((res) => {
            const debut = new Date(res.datetimeDebut);
            const fin = new Date(res.datetimeFin);
            console.log('reservation:', debut, fin);

            if (filtreFin) {
              if (filtreDebut.getTime() < fin.getTime() && filtreFin.getTime() > debut.getTime()) {
                ok = false;
                console.log(
                  '=> rejetée pour chevauchement avec filtreDebut:',
                  filtreDebut,
                  'filtreFin:',
                  filtreFin
                );
              }
            } else {
              if (filtreDebut.getTime() < fin.getTime()) {
                ok = false;
                console.log('=> rejetée pour chevauchement sans filtreFin');
              }
            }
          });
        }

        return ok;
      });
    });
  }


  choisirTable(tableJeu : TableJeu){
    this.tableSelectionne = tableJeu;
    this.tableChoisi = true;
  }

  appliquerFiltreJeu(){
    
  }


}
