import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Inbox } from '../models/Inbox';

@Injectable({
  providedIn: 'root'
})
export class InboxService {
    
  
  public host  = environment.apiUrl;
  
  constructor(private http: HttpClient) { }

  // * Get list of inbox
  public getAllMessages():  Observable<Inbox[]> {
    return this.http.get<Inbox[]>(`${this.host}/inbox/list`)
  }
  
  // * Get inbox by ID
  public getMessageById(id : string) : Observable<Inbox> {
      return this.http.get<Inbox>(`${this.host}/inbox/view/${id}`)
  }
   
  // * Agregar inbox
  public sendMessage(formData: any): Observable<Inbox> {
      return this.http.post<Inbox>(`${this.host}/inbox/contact`, formData);
  }

  // * Delete inbox
  public deleteMessage(id: string): Observable<Inbox> {
    return this.http.delete<Inbox>(`${this.host}/inbox/delete/${id}`);
  }

  
}
