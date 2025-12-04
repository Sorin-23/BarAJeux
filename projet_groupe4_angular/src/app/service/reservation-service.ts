import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap } from 'rxjs';
import { Reservation } from '../dto/reservation';
import { Top } from '../dto/top';
import { Employe } from '../dto/employe';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  
  private apiUrl: string = '/reservation';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Reservation[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.http.get<Reservation[]>(this.apiUrl))
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  public findById(id: number): Observable<Reservation> {
    return this.http.get<Reservation>(`${this.apiUrl}/${id}`);
  }

  public save(reservationDto: Reservation): void {
    const payload = reservationDto.toJson(); // { nom: this.nom }

    if (!reservationDto.id) {
      this.http.post<Reservation>(this.apiUrl, payload)
        .subscribe(() => this.refresh());
    } else {
      this.http.put<Reservation>(`${this.apiUrl}/${reservationDto.id}`, payload)
        .subscribe(() => this.refresh());
    }
  }

  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`)
      .subscribe(() => this.refresh());
  }

  public getTopReservations(): Observable<Top[]>{
    return this.http.get<Top[]>(`/api/top/reservations`);
}
public findAllGameMasters(): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.apiUrl}/gamemasters`);
}


  //pour annulér une réservation
  public cancel(id: number): Observable<void> {
    //  PUT /api/reservation/{id}/cancel
    return this.http.put<void>(`${this.apiUrl}/${id}/cancel`, {});
  }

}
