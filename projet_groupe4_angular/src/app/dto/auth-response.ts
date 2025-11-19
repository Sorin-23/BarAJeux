export class AuthResponse {
  constructor(
    private _token: string,
    private _nom: string,
    private _prenom: string,
    private _username: string,
    private _role : string,
  ) {}

  public get token(): string {
    return this._token;
  }

  public set token(value: string) {
    this._token = value;
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

  public get username(): string {
    return this._username;
  }
  public set username(value: string) {
    this._username = value;
  }
  public get role():string{
    return this._role;
  }

  public set role(value:string){
    this._role = value;
  }

}
