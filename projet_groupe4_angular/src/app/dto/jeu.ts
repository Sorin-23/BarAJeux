import { CategorieJeu } from "./enum/categorie-jeu";
import { TypeJeu } from "./enum/type-jeu";

export class Jeu {
    constructor(
    public _id: number,
    public _nom: string,
    public _typesJeux: TypeJeu[] = [],
    public _ageMinimum: number ,
    public _nbJoueurMinimum: number,
    public _nbJoueurMaximum: number,
    public _duree: number,
    public _nbExemplaire: number ,
    public _note: number,
    public _categoriesJeux: CategorieJeu[] = [],
    public _imgURL?: string,
    public _besoinGameMaster: boolean = false
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

  public get typesJeux(): TypeJeu[] {
    return this._typesJeux;
  }
  public set typesJeux(value: TypeJeu[]) {
    this._typesJeux = value;
  }

  public get ageMinimum(): number {
    return this._ageMinimum;
  }
  public set ageMinimum(value: number) {
    this._ageMinimum = value;
  }

  public get nbJoueurMinimum(): number {
    return this._nbJoueurMinimum;
  }
  public set nbJoueurMinimum(value: number) {
    this._nbJoueurMinimum = value;
  }

  public get nbJoueurMaximum(): number {
    return this._nbJoueurMaximum;
  }
  public set nbJoueurMaximum(value: number) {
    this._nbJoueurMaximum = value;
  }

  public get duree(): number {
    return this._duree;
  }
  public set duree(value: number) {
    this._duree = value;
  }

  public get nbExemplaire(): number {
    return this._nbExemplaire;
  }
  public set nbExemplaire(value: number) {
    this._nbExemplaire = value;
  }

  public get note(): number {
    return this._note;
  }
  public set note(value: number) {
    this._note = value;
  }

  public get categoriesJeux(): CategorieJeu[] {
    return this._categoriesJeux;
  }
  public set categoriesJeux(value: CategorieJeu[]) {
    this._categoriesJeux = value;
  }

  public get imgURL(): string | undefined {
    return this._imgURL;
  }
  public set imgURL(value: string | undefined) {
    this._imgURL = value;
  }

  public get besoinGameMaster(): boolean {
    return this._besoinGameMaster;
  }
  public set besoinGameMaster(value: boolean) {
    this._besoinGameMaster = value;
  }

  // toJson()
  public toJson(): any {
    return {
      nom: this._nom,
      typesJeux: this._typesJeux,
      ageMinimum: this._ageMinimum,
      nbJoueurMinimum: this._nbJoueurMinimum,
      nbJoueurMaximum: this._nbJoueurMaximum,
      duree: this._duree,
      nbExemplaire: this._nbExemplaire,
      note: this._note,
      categoriesJeux: this._categoriesJeux,
      imgURL: this._imgURL || null,
      besoinGameMaster: this._besoinGameMaster
    };
  }

}
