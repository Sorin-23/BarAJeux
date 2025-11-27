import { Reservation } from "./reservation";

export class Avis {
    constructor(
        public _id: number,
        public _note:number,
        public _titre:string,
        public _commentaire?:string,
        public _reservation? : Reservation
    ){}

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get note(): number {
        return this._note;
    }

    public set note(value: number) {
        this._note = value;
    }

    public get titre(): string {
        return this._titre;
    }

    public set titre(value: string) {
        this._titre = value;
    }

    public get commentaire(): string | undefined{
        return this._commentaire;
    }

    public set commentaire(value: string | undefined) {
        this._commentaire = value;
    }

    public get reservation(): Reservation | undefined{
        return this._reservation;
    }

    public set reservation(value: Reservation | undefined) {
        this._reservation = value;
    }

    public toJson():any{
        return {
            note: this._note,
            titre: this._titre,
            commentaire: this._commentaire,
            reservation: this._reservation ? this._reservation.toJson?.() ?? this._reservation : undefined
        };
    }
}
