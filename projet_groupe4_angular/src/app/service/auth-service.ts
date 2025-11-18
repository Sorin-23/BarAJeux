import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest } from '../dto/auth-request';
import { AuthResponse } from '../dto/auth-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private _token: string= "";
  constructor(private http:HttpClient){
    this._token = sessionStorage.getItem("token")?? "";
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
          resolve();
        },
        error:err =>reject(err)
      });
    })
  }
}
