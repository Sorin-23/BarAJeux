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
      // 1. Authenticate with the backend
      // We expect the service to return the response object (JSON)
      const response: any = await this.authService.auth(
        new AuthRequest(this.usernameCtrl.value, this.passwordCtrl.value)
      );

      //token pour l'interceptor
      if (response && response.token) {
        localStorage.setItem('token', response.token);
      }
      
      
      localStorage.setItem('username', this.usernameCtrl.value);

     
      // Removing window.location.reload() prevents variables from being wiped out
      this.router.navigate(['/home']).then(() => {
    window.location.reload(); // reload uniquement après navigation
  });
      
    } catch (error) {
      console.error("Login failed:", error);
      this.loginError = true;
    }
  }
}