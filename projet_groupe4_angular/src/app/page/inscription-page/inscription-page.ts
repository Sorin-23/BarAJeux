import { Component, OnInit } from '@angular/core';
import { PersonneService } from '../../service/personne-service';
import { Router, RouterLink } from '@angular/router';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SubscribeClientRequest } from '../../dto/subscribe-client-request';
import { passwordMatchValidator } from '../../validator/password-match-validator';

@Component({
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './inscription-page.html',
  styleUrl: './inscription-page.css',
})
export class InscriptionPage implements OnInit {
  protected subscriptionError: boolean = false;

  protected clientForm!: FormGroup;

  protected nomCtrl!: FormControl;
  protected prenomCtrl!: FormControl;
  protected mailCtrl!: FormControl;
  protected mdpCtrl!: FormControl;
  protected mdpConfirmCtrl!: FormControl;
  protected telephoneCtrl!: FormControl;
  protected villeCtrl!: FormControl;
  protected codePostaleCtrl!: FormControl;
  protected adresseCtrl!: FormControl;

  constructor(
    private personneService: PersonneService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.nomCtrl = this.formBuilder.control('', Validators.required);
    this.prenomCtrl = this.formBuilder.control('', Validators.required);
    this.mailCtrl = this.formBuilder.control('', [Validators.required, Validators.email]);
    this.mdpCtrl = this.formBuilder.control('', [Validators.required, Validators.minLength(6)]);
    this.mdpConfirmCtrl = this.formBuilder.control('', [
      Validators.required,
      Validators.minLength(6),
    ]);
    this.telephoneCtrl = this.formBuilder.control('', Validators.required);
    this.villeCtrl = this.formBuilder.control('', Validators.required);
    this.codePostaleCtrl = this.formBuilder.control('', Validators.required);
    this.adresseCtrl = this.formBuilder.control('', Validators.required);

    this.clientForm = this.formBuilder.group(
      {
        nom: this.nomCtrl,
        prenom: this.prenomCtrl,
        mail: this.mailCtrl,
        mdp: this.mdpCtrl,
        mdpConfirm: this.mdpConfirmCtrl,
        telephone: this.telephoneCtrl,
        ville: this.villeCtrl,
        codePostale: this.codePostaleCtrl,
        adresse: this.adresseCtrl,
      },
      {
        validators: passwordMatchValidator('password', 'passwordConfirm'),
      }
    );
  }
  public async connecter() {
    try {
      await this.personneService.subscribe(
        new SubscribeClientRequest(
          this.nomCtrl.value,
          this.prenomCtrl.value,
          this.mailCtrl.value,
          this.mdpCtrl.value,
          this.telephoneCtrl.value,
          this.villeCtrl.value,
          this.codePostaleCtrl.value,
          this.adresseCtrl.value,
          new Date(), // dateCreation
          new Date(), // dateLastConnexion
          0 // pointFidelite
        )
      );

      this.router.navigate(['/login']);
    } catch {
      // Si la connexion n'a pas pu se faire, affichage de l'erreur sur le template
      this.subscriptionError = true;
    }
  }
}
