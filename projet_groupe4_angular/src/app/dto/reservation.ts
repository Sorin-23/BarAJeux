import { Avis } from './avis';
import { Client } from './client';
import { Employe } from './employe';
import { StatutReservation } from './enum/statut-reservation';
import { Jeu } from './jeu';
import { TableJeu } from './table-jeu';

export class Reservation {
  constructor(
    public _id: number,
    public _datetimeDebut: Date,
    public _datetimeFin: Date,
    public _nbJoueur: number,
    public _tableJeu: TableJeu,
    public _jeu: Jeu,
    public _statutReservation: StatutReservation,
    public _client: Client,
    public _gameMaster: Employe,
    public _tableID?: number,
    public _avis?: Avis,
    public _avisModifiable?: boolean
  ) {}

  public get id(): number {
    return this._id;
  }
  public set id(value: number) {
    this._id = value;
  }

  public get datetimeDebut(): Date {
    return this._datetimeDebut;
  }
  public set datetimeDebut(value: Date) {
    this._datetimeDebut = value;
  }

  public get datetimeFin(): Date {
    return this._datetimeFin;
  }
  public set datetimeFin(value: Date) {
    this._datetimeFin = value;
  }

  public get nbJoueur(): number {
    return this._nbJoueur;
  }
  public set nbJoueur(value: number) {
    this._nbJoueur = value;
  }

  public get tableJeu(): TableJeu {
    return this._tableJeu;
  }
  public set tableJeu(value: TableJeu) {
    this._tableJeu = value;
  }

  public get jeu(): Jeu {
    return this._jeu;
  }
  public set jeu(value: Jeu) {
    this._jeu = value;
  }

  public get statutReservation(): StatutReservation {
    return this._statutReservation;
  }
  public set statutReservation(value: StatutReservation) {
    this._statutReservation = value;
  }

  public get client(): Client {
    return this._client;
  }
  public set client(value: Client) {
    this._client = value;
  }

  public get gameMaster(): Employe {
    return this._gameMaster;
  }
  public set gameMaster(value: Employe) {
    this._gameMaster = value;
  }
  public get tableID(): number | undefined {
    return this._tableID;
  }
  public set tableID(value: number | undefined) {
    this._tableID = value;
  }
  public get avis(): Avis | undefined {
    return this._avis;
  }
  public set avis(value: Avis | undefined) {
    this._avis = value;
  }
    public get avisModifiable(): boolean| undefined{
    return this._avisModifiable;
  }
  public set avisModifiable(value: boolean | undefined) {
    this._avisModifiable = value;
  }

  public toJson(): any {
    return {
      datetimeDebut: this._datetimeDebut ? this.toBackendFormat(this._datetimeDebut) : null,
      datetimeFin: this._datetimeFin ? this.toBackendFormat(this._datetimeFin) : null,
      nbJoueur: this._nbJoueur,
      tableJeuId: this._tableJeu?.id ?? this._tableID?? null,
      jeuId: this._jeu?.id ?? null,
      statutReservation: this._statutReservation,
      clientId: this._client?.id ?? null,
      gameMasterId: this._gameMaster?.id ?? null,
      
    };
  }

  private toBackendFormat(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`; // format accepté par Spring
  }

  
}
