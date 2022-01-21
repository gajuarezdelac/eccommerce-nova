import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Order } from '../models/Order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  public host  = environment.apiUrl;

  constructor(private http: HttpClient) { }

   // * Get list of inbox
   public getAllOrders():  Observable<Order[]> {
    return this.http.get<Order[]>(`${this.host}/order/list`)
  }

   // * Get list of inbox
   public getOrdersByUser(userId : string):  Observable<Order[]> {
    return this.http.get<Order[]>(`${this.host}/order/list`)
   }
  
  // * Get inbox by ID
  public getOrderById(id : string) : Observable<Order> {
      return this.http.get<Order>(`${this.host}/order/view/${id}`)
  }

  // * Crear orden
  public createOrder(formData: any): Observable<Order> {
    return this.http.post<Order>(`${this.host}/order/create`, formData);
  }

  // * Crear orden
  public changeStatus(formData: any): Observable<Order> {
    return this.http.put<Order>(`${this.host}/order/change-status`, formData);
  }
  
  // * Delete inbox
  public deleteOrder(id: string): Observable<Order> {
    return this.http.delete<Order>(`${this.host}/order/delete/${id}`);
  }

















}
