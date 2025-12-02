import { Reservation } from './reservation';

export class ClientWithReservationResponse {
  constructor(
    private _id: number,
    private _nom: string,
    private _prenom: string,
    private _email: string,
    private _telephone: string,
    private _reservations: Reservation[]
  ) {}

  public get id(): number {
    return this._id;
  }

  public set id(value: number) {
    this._id = value;
  }

  public get nom(): string {
    return this._nom;
  }

  public set nom(value: string) {
    this._nom = value;
  }

  public get prenom(): string {
    return this._prenom;
  }

  public set prenom(value: string) {
    this._prenom = value;
  }

  public get email(): string {
    return this._email;
  }

  public set email(value: string) {
    this._email = value;
  }

  public get telephone(): string {
    return this._telephone;
  }

  public set telephone(value: string) {
    this._telephone = value;
  }

  public get reservations(): Reservation[] {
    return this._reservations;
  }

  public set reservations(value: Reservation[]) {
    this._reservations = value;
  }

  public toJson(): any {
    return {
      nom: this.nom,
      prenom: this.prenom,
      email: this.email,
      telephone: this.telephone,
      reservations: this.reservations,
    };
  }
}
