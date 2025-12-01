import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthRequest } from '../../dto/auth-request';
import { AuthService } from '../../service/auth-service';

@Component({
  selector: 'app-login-page',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage implements OnInit {
  protected loginError: boolean = false;
  protected userForm!: FormGroup;
  protected usernameCtrl!: FormControl;
  protected passwordCtrl!: FormControl;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usernameCtrl = this.formBuilder.control('', Validators.required);
    this.passwordCtrl = this.formBuilder.control('', [
      Validators.required,
      Validators.minLength(6),
    ]);

    this.userForm = this.formBuilder.group({
      username: this.usernameCtrl,
      password: this.passwordCtrl,
    });
  }

  public async connecter() {
    try {
      // La méthode auth renvoyant une Promise, on peut attendre la résolution avec "await"
      await this.authService.auth(
        new AuthRequest(this.usernameCtrl.value, this.passwordCtrl.value)
      );

      // Si tout est OK, on va sur la page d'accueil
      this.router.navigate(['/home']).then(() => {
        window.location.reload(); // reload uniquement après navigation
      });
    } catch {
      // Si la connexion n'a pas pu se faire, affichage de l'erreur sur le template
      this.loginError = true;
    }
  }
}
