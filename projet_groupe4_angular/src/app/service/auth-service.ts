import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest } from '../dto/auth-request';
import { AuthResponse } from '../dto/auth-response';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private _token: string= "";
  //private _nom: string = "";
  //private _prenom: string = "";
  private _username: string = "";
  private _role: string = "";

  private loggedIn = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.loggedIn.asObservable(); 


  constructor(private http:HttpClient){
    this._token = sessionStorage.getItem("token")?? "";
    //this._nom = sessionStorage.getItem("nom") ?? "";
    //this._prenom = sessionStorage.getItem("prenom") ?? "";
    this._username = sessionStorage.getItem("username") ?? "";
     this._role = sessionStorage.getItem("role") ?? "";
    
    this.loggedIn.next(!!this._token);
  }

  public get token():string{
    return this._token;
  }
/*
  public get nom(): string {
    return this._nom;
  }

  public get prenom(): string {
    return this._prenom;
  }
*/
  public get username(): string {
    return this._username;
  }

   public get role(): string {
    return this._role;
  }



  

  public auth(authRequest:AuthRequest):Promise<void>{
    return new Promise((resolve,reject)=>{
      this.http.post<AuthResponse>('/auth',authRequest.toJson()).subscribe({
        next:resp => {
          this._token = resp.token;
          /*this._nom = resp.nom;
          this._prenom = resp.prenom;*/
          this._username = resp.username;
          this._role = resp.role;

          sessionStorage.setItem("token",this._token);
          /*sessionStorage.setItem("nom", this._nom);
          sessionStorage.setItem("prenom", this._prenom);*/
          sessionStorage.setItem("username", this._username);
          sessionStorage.setItem("role", this._role);
          
          this.loggedIn.next(true);
          resolve();
        },
        error:err =>reject(err)
      });
    })
  }

  public logout(): void {
    this._token = "";
    /*this._nom = "";
    this._prenom = "";*/
    this._username = "";
    this._role = "";

    sessionStorage.removeItem("token");
    /*sessionStorage.removeItem("nom");
    sessionStorage.removeItem("prenom");*/
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("role");
    
    this.loggedIn.next(false);
  }
}
