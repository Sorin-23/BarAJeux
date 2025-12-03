import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, Observable, startWith, switchMap, map } from 'rxjs';
import { Client } from '../dto/client';
import { ClientWithReservationResponse } from '../dto/client-with-reservation-response';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  // Backend controller is @RequestMapping("/api/client")
  private apiUrl: string = '/client';
  private refresh$: Subject<void> = new Subject<void>();

  constructor(private http: HttpClient) {}

  //  FIND ALL
  public findAll(): Observable<Client[]> {
    return this.refresh$.pipe(
      startWith(null),
      switchMap(() =>
        this.http
          .get<any[]>(this.apiUrl)
          .pipe(map((data) => data.map((json) => this.mapJsonToClient(json))))
      )
    );
  }

  public refresh(): void {
    this.refresh$.next();
  }

  //  FIND BY ID 
  public findById(id: number): Observable<Client> {
    return this.http
      .get<any>(`${this.apiUrl}/${id}`)
      .pipe(map((json) => this.mapJsonToClient(json)));
  }

  // FIND BY USERNAME 
  public findByUsername(username: string): Observable<Client> {
    const encodedUsername = encodeURIComponent(username);
    return this.http
      .get<any>(`${this.apiUrl}/username/${encodedUsername}`)
      .pipe(map((json) => this.mapJsonToClient(json)));
  }



  //  SAVE (POST vs PUT)
  public save(client: Client): void {
    const payload: any = client.toJson();

    // Normalize LocalDate fields: backend expects "yyyy-MM-dd"
    const formatDateOnly = (value: Date | string | null | undefined): string | null => {
      if (!value) return null;
      const d = value instanceof Date ? value : new Date(value);
      if (isNaN(d.getTime())) return null;
      return d.toISOString().substring(0, 10); // "2025-10-13"
    };

    // Only set if they exist in DTO / backend request
    payload.dateCreation = formatDateOnly(client.dateCreation);
    payload.dateLastConnexion = formatDateOnly(client.dateLastConnexion);

    console.log('Saving Client. ID:', client.id);

    if (!client.id) {
      // Create
      this.http.post<Client>(this.apiUrl, payload).subscribe(() => this.refresh());
    } else {
      // Update
      this.http.put<Client>(`${this.apiUrl}/${client.id}`, payload).subscribe(() => this.refresh());
    }
  }

public changePassword(clientId: number, oldPassword: string, newPassword: string): Observable<string> {
  const payload = { oldPassword, newPassword };
  return this.http.put<string>(`${this.apiUrl}/${clientId}/password`, payload, { responseType: 'text' as 'json' });
}


  // DELETE 
  public deleteById(id: number): void {
    this.http.delete<void>(`${this.apiUrl}/${id}`).subscribe(() => this.refresh());
  }

  // RESERVATIONS et AVIS 
  // matche @GetMapping("/reservations/{id}")
  public getReservations(clientId: number): Observable<ClientWithReservationResponse> {
    return this.http.get<ClientWithReservationResponse>(`${this.apiUrl}/reservations/${clientId}`);
  }

  public saveAvis(reservationId: number, avisData: any): Observable<void> {
    
    return this.http.post<void>(`/avis`, avisData);
  }

  public createEmptyClient(): Client {
    return new Client(0, '', '', '', '', undefined, 0, new Date(), new Date(), [], [], '', '', '');
  }

  // MAP JSON TO CLIENT CLASS
  private mapJsonToClient(json: any): Client {
    if (!json) return this.createEmptyClient();

    return new Client(
      json.id,
      json.nom,
      json.prenom,
      json.mail,
      json.mdp || json.motDePasse,
      json.telephone || json.numeroTelephone,
      json.pointFidelite || json.point_fidelite,
      new Date(json.dateCreation || json.date_creation),
      new Date(json.dateLastConnexion || json.date_derniere_connexion),
      [],
      [],
      json.ville,
      json.codePostale || json.code_postale,
      json.adresse
    );
  }
}
