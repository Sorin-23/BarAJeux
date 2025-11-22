import { Client } from "./client";
import { Employe } from "./employe";
import { StatutReservation } from "./enum/statut-reservation";
import { Jeu } from "./jeu";
import { TableJeu } from "./table-jeu";

export class Reservation {
constructor(
        private _id: number,
        private _datetimeDebut: Date,
        private _datetimeFin: Date,
        private _nbJoueur: number,
        private _tableJeu: TableJeu,
        private _jeu: Jeu,
        private _statutReservation: StatutReservation,
        private _client: Client,
        private _gameMaster: Employe
    ) {}

    public get id(): number { return this._id; }
    public set id(value: number) { this._id = value; }

    public get datetimeDebut(): Date { return this._datetimeDebut; }
    public set datetimeDebut(value: Date) { this._datetimeDebut = value; }

    public get datetimeFin(): Date { return this._datetimeFin; }
    public set datetimeFin(value: Date) { this._datetimeFin = value; }

    public get nbJoueur(): number { return this._nbJoueur; }
    public set nbJoueur(value: number) { this._nbJoueur = value; }

    public get tableJeu(): TableJeu  { return this._tableJeu; }
    public set tableJeu(value: TableJeu ) { this._tableJeu = value; }

    public get jeu(): Jeu  { return this._jeu; }
    public set jeu(value: Jeu ) { this._jeu = value; }

    public get statutReservation(): StatutReservation  { return this._statutReservation; }
    public set statutReservation(value: StatutReservation ) { this._statutReservation = value; }

    public get client(): Client  { return this._client; }
    public set client(value: Client ) { this._client = value; }

    public get gameMaster(): Employe { return this._gameMaster; }
    public set gameMaster(value: Employe ) { this._gameMaster = value; }

    public toJson(): any {
        return {
            datetimeDebut: this._datetimeDebut,
            datetimeFin: this._datetimeFin,
            nbJoueur: this._nbJoueur,
            tableJeu: this._tableJeu?.toJson(),
            jeu: this._jeu?.toJson(),
            statutReservation: this._statutReservation,
            client: this._client?.toJson(),
            gameMaster: this._gameMaster?.toJson()
        };
    }
}
