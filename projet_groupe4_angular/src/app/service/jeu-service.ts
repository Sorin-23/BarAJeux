import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Jeu } from '../dto/jeu';

@Injectable({
  providedIn: 'root',
})
export class JeuService {
  
  private apiUrl: string = '/jeu';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Jeu[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Jeu[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Jeu> {
    return this.http.get<Jeu>(`${this.apiUrl}/${id}`);
  }

  public save(jeuDto: Jeu): void {
    const payload = jeuDto.toJson(); // { nom: this.nom }

    if (!jeuDto.id) {
      this.http.post<Jeu>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Jeu>(`${this.apiUrl}/${jeuDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }

  public findDisponibles(dateDebut: string): Observable<Jeu[]> {
  return this.http.get<Jeu[]>(`${this.apiUrl}/disponibles?dateDebut=${dateDebut}`);
}

}
