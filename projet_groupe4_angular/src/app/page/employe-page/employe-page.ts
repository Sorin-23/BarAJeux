import { Component, OnInit } from '@angular/core';
import { Employe } from '../../dto/employe'; 
import { EmployeService } from '../../service/employe-service';
import { AuthService } from '../../service/auth-service';
import { CommonModule } from '@angular/common';
import { FormGroup, FormControl, Validators, AbstractControl, ReactiveFormsModule, FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employe-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './employe-page.html',
  styleUrl: './employe-page.css',
})
export class EmployePage implements OnInit {
  
  
  username!: string;
  employeID!: number;
  msg: string = "";
  passwordForm: FormGroup;

  
  currentEmploye: Employe | null = null;
  isEditingProfile: boolean = false; 
  validationPassword: string = ""; 

  constructor(
    private employeService: EmployeService,
    private authService: AuthService
  ) {
    
    this.passwordForm = new FormGroup({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', Validators.required)
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void {
    this.username = this.authService.username;
    
   
    this.employeService.findByUsername(this.username).subscribe({
      next: (employe) => {
        this.currentEmploye = employe;
        this.employeID = employe.id;
       
        this.validationPassword = ""; 
       
        if(this.currentEmploye) this.currentEmploye.mdp = "";
      },
      error: (err) => console.error("Erreur chargement profil", err)
    });
  }

  
  passwordMatchValidator(group: AbstractControl): { [key: string]: boolean } | null {
    const password = group.get('newPassword')?.value;
    const confirm = group.get('confirmPassword')?.value;
    return password === confirm ? null : { passwordMismatch: true };
  }

  changePassword() {
    if (this.passwordForm.invalid) {
      this.msg = "Veuillez corriger le formulaire de mot de passe.";
      return;
    }
    const oldPassword = this.passwordForm.value.oldPassword.trim();
    const newPassword = this.passwordForm.value.newPassword.trim();

    this.employeService.updatePassword(this.employeID, oldPassword, newPassword)
      .subscribe({
        next: () => {
          this.msg = "Mot de passe modifié avec succès !";
          this.passwordForm.reset();
        },
        error: err => {
          if (err.status === 403) this.msg = "Ancien mot de passe incorrect.";
          else this.msg = "Erreur serveur.";
        }
      });
  }

  

  toggleEditProfile() {
    this.isEditingProfile = !this.isEditingProfile;
    if (!this.isEditingProfile) {
      
      this.ngOnInit();
    }
  }

  saveProfile() {
    if (!this.currentEmploye) return;

    
    if (!this.validationPassword || this.validationPassword.trim() === '') {
      alert("Veuillez entrer votre mot de passe actuel pour valider les modifications.");
      return;
    }

    
    this.currentEmploye.mdp = this.validationPassword;

    
    this.employeService.save(this.currentEmploye); 
    
    
    alert("Modifications envoyées !");
    this.isEditingProfile = false;
    this.validationPassword = ""; 
  }
}