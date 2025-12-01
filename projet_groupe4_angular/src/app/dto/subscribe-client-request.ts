export class SubscribeClientRequest {
  constructor(
    private _nom: string,
    private _prenom: string,
    private _mail: string,
    private _mdp: string,
    private _telephone: string,
    private _ville: string,
    private _codePostale: string,
    private _adresse: string,
    private _dateCreation: string,
    private _dateLastConnexion: string,
    private _pointFidelite: number
  ) {}

  public get dateCreation(): string {
    return this._dateCreation;
  }

  public set dateCreation(value: string) {
    this._dateCreation = value;
  }

  public get dateLastConnexion(): string {
    return this._dateLastConnexion;
  }

  public set dateLastConnexion(value: string) {
    this._dateLastConnexion = value;
  }

  public get pointFidelite(): number {
    return this._pointFidelite;
  }

  public set pointFidelite(value: number) {
    this._pointFidelite = value;
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

  public get mail(): string {
    return this._mail;
  }

  public set mail(value: string) {
    this._mail = value;
  }

  public get mdp(): string {
    return this._mdp;
  }

  public set mdp(value: string) {
    this._mdp = value;
  }

  public get telephone(): string {
    return this._telephone;
  }

  public set telephone(value: string) {
    this._telephone = value;
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

  public toJson(): any {
    return {
      nom: this.nom,
      prenom: this.prenom,
      mail: this.mail,
      mdp: this.mdp,
      telephone: this.telephone,
      ville: this.ville,
      codePostale: this.codePostale,
      adresse: this.adresse,
      dateCreation: this.dateCreation,
      dateLastConnexion: this.dateLastConnexion,
      pointFidelite: this.pointFidelite,
    };
  }
}
