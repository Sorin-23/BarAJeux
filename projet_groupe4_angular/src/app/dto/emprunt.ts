import { Client } from "./client";
import { StatutLocation } from "./enum/statut-location";
import { Jeu } from "./jeu";

export class Emprunt {
constructor(
        public _id: number,
        public _dateEmprunt: Date,
        public _dateRetour: Date,
        public _statutLocation: StatutLocation,
        
        public _client: Client,
        public _jeu: Jeu,
        public _dateRetourReel?: Date,
    ) {}

    public get id(): number {
        return this._id;
    }
    public set id(value: number) {
        this._id = value;
    }

    public get dateEmprunt(): Date {
        return this._dateEmprunt;
    }
    public set dateEmprunt(value: Date) {
        this._dateEmprunt = value;
    }

    public get dateRetour(): Date {
        return this._dateRetour;
    }
    public set dateRetour(value: Date) {
        this._dateRetour = value;
    }

    public get statutLocation(): StatutLocation {
        return this._statutLocation;
    }
    public set statutLocation(value: StatutLocation) {
        this._statutLocation = value;
    }

    public get dateRetourReel(): Date | undefined {
        return this._dateRetourReel;
    }
    public set dateRetourReel(value: Date | undefined) {
        this._dateRetourReel = value;
    }

    public get client(): Client  {
        return this._client;
    }
    public set client(value: Client ) {
        this._client = value;
    }

    public get jeu(): Jeu  {
        return this._jeu;
    }
    public set jeu(value: Jeu ) {
        this._jeu = value;
    }

    public toJson(): any {
        return {
            dateEmprunt: this._dateEmprunt,
            dateRetour: this._dateRetour,
            statutLocation: this._statutLocation,
            dateRetourReel: this._dateRetourReel,
            client: this._client?.toJson(),
            jeu: this._jeu?.toJson()
        };
    }

}
