import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../service/auth-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  imports: [CommonModule, RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header implements OnInit {
  public role: string = '';
  isLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthService, private router : Router) {
    this.isLoggedIn$ = this.authService.isLoggedIn$;
  }

  ngOnInit(): void {
    this.role = this.authService.role;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}
