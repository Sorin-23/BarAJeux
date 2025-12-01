import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Top } from '../dto/top';

@Injectable({
  providedIn: 'root',
})
export class TopService {
   private apiUrl: string = '/top';

  constructor(private http: HttpClient) {}

  getTopEmprunts(): Observable<Top[]> {
    return this.http.get<Top[]>(`${this.apiUrl}/emprunts`);
  }

  getTopReservations(): Observable<Top[]> {
    return this.http.get<Top[]>(`${this.apiUrl}/reservations`);
  }

  getTopNotes(): Observable<Top[]> {
    return this.http.get<Top[]>(`${this.apiUrl}/notes`);
  }
}
