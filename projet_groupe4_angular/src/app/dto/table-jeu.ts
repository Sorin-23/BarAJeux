import { Reservation } from "./reservation";

export class TableJeu {


    constructor(
    public _id: number,
    public _nomTable: string,
    public _capacite: number,
    public _imgUrl : string,
    public _reservations?: Reservation[]
    
  ) {}

  // Getters & Setters
  public get id(): number {
    return this._id;
  }
  public set id(value: number) {
    this._id = value;
  }

  public get nomTable(): string {
    return this._nomTable;
  }
  public set nomTable(value: string) {
    this._nomTable = value;
  }

  public get capacite(): number {
    return this._capacite;
  }
  public set capacite(value: number) {
    this._capacite = value;
  }



  public get reservations(): Reservation[]| undefined {
    return this._reservations;
  }
  public set reservations(value: Reservation[]| undefined) {
    this._reservations = value;
  }

    public get imgUrl(): string {
    return this._imgUrl;
  }
  public set imgUrl(value: string) {
    this._imgUrl = value;
  }

  public toJson(): any {
    return {
      nomTable: this._nomTable,
      capacite: this._capacite,
      reservations: this._reservations?.map(r => r.toJson())|| [],
      imgUrl:this._imgUrl
    };
  }
}
