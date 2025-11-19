import { Emprunt } from "./emprunt";
import { Personne } from "./personne";
import { Reservation } from "./reservation";

export class Client extends Personne {
    constructor(
        id: number,
        nom: string,
        prenom: string,
        mail: string,
        mdp: string,
        telephone: string | undefined,
        private _pointFidelite: number,
        private _dateCreation: Date,
        private _dateLastConnexion: Date,
        private _reservations: Reservation[] = [],
        private _emprunts: Emprunt[] = [],
        private _ville: string,
        private _codePostale: string,
        private _adresse: string
    ) { super(id, nom, prenom, mail, mdp, telephone); }

    public get pointFidelite(): number {
        return this._pointFidelite;
    }
    public set pointFidelite(value: number) {
        this._pointFidelite = value;
    }

    public get dateCreation(): Date {
        return this._dateCreation;
    }
    public set dateCreation(value: Date) {
        this._dateCreation = value;
    }

    public get dateLastConnexion(): Date {
        return this._dateLastConnexion;
    }
    public set dateLastConnexion(value: Date) {
        this._dateLastConnexion = value;
    }

    public get reservations(): Reservation[] {
        return this._reservations;
    }
    public set reservations(value: Reservation[]) {
        this._reservations = value;
    }

    public get emprunts(): Emprunt[] {
        return this._emprunts;
    }
    public set emprunts(value: Emprunt[]) {
        this._emprunts = value;
    }

    public get ville(): string {
        return this._ville;
    }
    public set ville(value: string) {
        this._ville = value;
    }

    public get codePostale(): string {
        return this._codePostale;
    }
    public set codePostale(value: string) {
        this._codePostale = value;
    }

    public get adresse(): string {
        return this._adresse;
    }
    public set adresse(value: string) {
        this._adresse = value;
    }

    public override toJson(): any {
        return {
            ...super.toJson(),
            pointFidelite: this._pointFidelite,
            dateCreation: this._dateCreation,
            dateLastConnexion: this._dateLastConnexion,
            reservations: this._reservations,
            emprunts: this._emprunts,
            ville: this._ville,
            codePostale: this._codePostale,
            adresse: this._adresse
        };
    }
}
