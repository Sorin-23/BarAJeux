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
  private loggedIn = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.loggedIn.asObservable(); 
  constructor(private http:HttpClient){
    this._token = sessionStorage.getItem("token")?? "";
    this.loggedIn.next(!!this._token);
  }

  public get token():string{
    return this._token;
  }

  public auth(authRequest:AuthRequest):Promise<void>{
    return new Promise((resolve,reject)=>{
      this.http.post<AuthResponse>('/auth',authRequest.toJson()).subscribe({
        next:resp => {
          this._token = resp.token;

          sessionStorage.setItem("token",this._token);
          this.loggedIn.next(true);
          resolve();
        },
        error:err =>reject(err)
      });
    })
  }

  public logout(): void {
    this._token = "";
    sessionStorage.removeItem("token");
    this.loggedIn.next(false);
  }
}
