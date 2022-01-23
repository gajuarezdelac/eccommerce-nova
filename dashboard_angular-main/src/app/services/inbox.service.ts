import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Inbox } from '../models/Inbox';
import { Pagination } from '../models/Pagination';

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

  
  // * Get list of inbox
  public getAllMessagesPaginate(pagination : Pagination):  Observable<Inbox> {

    const params = new HttpParams({
      fromObject: {
        pageNo: pagination.numberPage,
        pageSize: pagination.sizePage
      }
    });

    return this.http.get<Inbox>(`${this.host}/inbox/paginate`,{ params: params } )
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
