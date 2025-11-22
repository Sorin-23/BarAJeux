import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Avis } from '../dto/avis';

@Injectable({
  providedIn: 'root',
})
export class AvisService {

  private apiUrl: string = '/avis';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Avis[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Avis[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Avis> {
    return this.http.get<Avis>(`${this.apiUrl}/${id}`);
  }

  public save(avisDto: Avis): void {
    const payload = avisDto.toJson(); // { nom: this.nom }

    if (!avisDto.id) {
      this.http.post<Avis>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Avis>(`${this.apiUrl}/${avisDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
  
}
