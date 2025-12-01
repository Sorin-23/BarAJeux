import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Personne } from '../dto/personne';
import { SubscribeClientRequest } from '../dto/subscribe-client-request';
import { SubscribeClientResponse } from '../dto/subscribe-client-response';

@Injectable({
  providedIn: 'root',
})
export class PersonneService {
  private apiUrl: string = '/personne';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Personne[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Personne[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Personne> {
    return this.http.get<Personne>(`${this.apiUrl}/${id}`);
  }

  public save(personneDto: Personne): void {
    const payload = personneDto.toJson(); // { nom: this.nom }

    if (!personneDto.id) {
      this.http.post<Personne>(this.apiUrl, payload).subscribe(() => this.refresh());
    } else {
      this.http
        .put<Personne>(`${this.apiUrl}/${personneDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`).subscribe(() => this.refresh());
  }

  public subscribe(request: SubscribeClientRequest): Promise<void> {
    return new Promise((resolve, reject) => {
      this.http.post<SubscribeClientResponse>('/inscription/client', request.toJson()).subscribe({
        // next => si la réponse est OK
        next: (resp) => {
          resolve();
        },

        // error => si la réponse est KO (30X, 40X, 50X)
        error: (err) => reject(err),
      });
    });
  }
}
