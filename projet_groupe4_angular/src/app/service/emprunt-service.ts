import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Emprunt } from '../dto/emprunt';
import { Top } from '../dto/top';

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

  public save(empruntDto: Emprunt): Observable<Emprunt> {
    const payload = empruntDto.toJson();

    if (!empruntDto.id) {
      return this.http.post<Emprunt>(this.apiUrl, payload);
    } else {
      return this.http.put<Emprunt>(`${this.apiUrl}/${empruntDto.id}`, payload);
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`).subscribe(() => this.refresh());
  }
  public getTopEmprunts(): Observable<Top[]> {
    return this.http.get<Top[]>(`/api/top/emprunts`);
  }
}
