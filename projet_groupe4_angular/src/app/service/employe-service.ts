import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap, map } from 'rxjs';
import { Employe } from '../dto/employe';

@Injectable({
  providedIn: 'root',
})
export class EmployeService {

  private apiUrl: string = '/employe';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Employe[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Employe[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Employe> {
    return this.http.get<Employe>(`${this.apiUrl}/${id}`);
  }

  public save(employeDto: Employe): void {
    const payload = employeDto.toJson(); // { nom: this.nom }

    if (!employeDto.id) {
      this.http.post<Employe>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Employe>(`${this.apiUrl}/${employeDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
  
  getAllGameMasters(): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.apiUrl}/gamemasters`);
  }

  updatePassword(id: number, oldPassword: string, newPassword: string) {
  return this.http.put(
    `${this.apiUrl}/${id}/password`,
    { oldPassword, newPassword }
  );
}

public findByUsername(username: string): Observable<Employe> {
    const encodedUsername = encodeURIComponent(username);
    return this.http
      .get<any>(`${this.apiUrl}/username/${encodedUsername}`)
      .pipe(map((json) => this.mapJsonToEmploye(json)));
  }


  private mapJsonToEmploye(json: any): Employe {
      if (!json) return this.createEmptyEmploye();
  
      return new Employe(
        json.id,
        json.nom,
        json.prenom,
        json.mail,
        json.mdp || json.motDePasse,
        json.telephone || json.numeroTelephone,
        json.job,
        json.gameMaster)

    }

    public createEmptyEmploye(): Employe {
        return new Employe(0, '', '', '', '', undefined, '', false);
      }
  
}
