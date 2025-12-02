import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest } from '../dto/auth-request';
import { AuthResponse } from '../dto/auth-response';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private _token: string = "";
  private _username: string = "";
  private _role: string = "";

  private loggedIn = new BehaviorSubject<boolean>(false);
  public isLoggedIn$ = this.loggedIn.asObservable(); 

  constructor(private http: HttpClient){
    // CHANGE: Use sessionStorage
    this._token = sessionStorage.getItem("token") ?? "";
    this._username = sessionStorage.getItem("username") ?? "";
    this._role = sessionStorage.getItem("role") ?? "";
    
    this.loggedIn.next(!!this._token);
  }

  public get token(): string { return this._token; }
  public get username(): string { return this._username; }
  public get role(): string { return this._role; }

  public auth(authRequest: AuthRequest): Promise<AuthResponse> {
    return new Promise((resolve, reject) => {
      this.http.post<AuthResponse>('/auth', authRequest.toJson()).subscribe({
        next: resp => {
          this._token = resp.token;
          this._username = resp.username;
          this._role = resp.role;

          // CHANGE: Save to sessionStorage
          sessionStorage.setItem("token", this._token);
          sessionStorage.setItem("username", this._username);
          sessionStorage.setItem("role", this._role);
          
          this.loggedIn.next(true);
          resolve(resp);
        },
        error: err => reject(err)
      });
    })
  }

  public logout(): void {
    this._token = "";
    this._username = "";
    this._role = "";

    // CHANGE: Clear sessionStorage
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("role");
    
    this.loggedIn.next(false);
  }
}
