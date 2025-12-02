import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
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
  
}
