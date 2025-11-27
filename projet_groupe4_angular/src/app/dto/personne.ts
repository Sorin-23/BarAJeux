export abstract class Personne {
  constructor(
    public _id: number,
    public _nom: string,
    public _prenom: string,
    public _mail: string,
    public _mdp: string,
    public _telephone?: string
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

  public get telephone(): string | undefined {
    return this._telephone;
  }
  public set telephone(value: string | undefined) {
    this._telephone = value;
  }

  public toJson(): any {
    return {
      nom: this._nom,
      prenom: this._prenom,
      mail: this._mail,
      mdp: this._mdp,
      telephone: this._telephone,
    };
  }
}
