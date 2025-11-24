import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-page',
  imports: [CommonModule, RouterLink],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage implements OnInit {
  public nom: string = '';
  public prenom: string = '';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.nom = this.authService.nom;
    this.prenom = this.authService.prenom;
  }
}
