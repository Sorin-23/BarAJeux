import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Emprunt } from '../dto/emprunt';

@Injectable({
  providedIn: 'root',
})
export class EmpruntService {
  
  private apiUrl: string = '/emprunt';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Emprunt[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Emprunt[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Emprunt> {
    return this.http.get<Emprunt>(`${this.apiUrl}/${id}`);
  }

  public save(empruntDto: Emprunt): void {
    const payload = empruntDto.toJson(); // { nom: this.nom }

    if (!empruntDto.id) {
      this.http.post<Emprunt>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Emprunt>(`${this.apiUrl}/${empruntDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
}
