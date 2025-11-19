import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Badge } from '../dto/badge';

@Injectable({
  providedIn: 'root',
})
export class BadgeService {
  
  private apiUrl: string = '/badge';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Badge[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Badge[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Badge> {
    return this.http.get<Badge>(`${this.apiUrl}/${id}`);
  }

  public save(badgeDto: Badge): void {
    const payload = badgeDto.toJson(); // { nom: this.nom }

    if (!badgeDto.id) {
      this.http.post<Badge>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Badge>(`${this.apiUrl}/${badgeDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
}
