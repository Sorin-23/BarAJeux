import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Client } from '../dto/client';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  
  private apiUrl: string = '/client';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Client[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Client[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}/${id}`);
  }

  public save(clientDto: Client): void {
    const payload = clientDto.toJson(); // { nom: this.nom }

    if (!clientDto.id) {
      this.http.post<Client>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Client>(`${this.apiUrl}/${clientDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }
}
