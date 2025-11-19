import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { TableJeu } from '../dto/table-jeu';

@Injectable({
  providedIn: 'root',
})
export class TableJeuService {
  
  private apiUrl: string = '/tableJeu';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<TableJeu[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<TableJeu[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<TableJeu> {
    return this.http.get<TableJeu>(`${this.apiUrl}/${id}`);
  }

  public save(tableJeuDto: TableJeu): void {
    const payload = tableJeuDto.toJson(); // { nom: this.nom }

    if (!tableJeuDto.id) {
      this.http.post<TableJeu>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<TableJeu>(`${this.apiUrl}/${tableJeuDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
}
