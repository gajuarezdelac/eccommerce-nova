import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Review } from '../models/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  
  public host  = environment.apiUrl;

  constructor(private http: HttpClient) { }

  // * Get all reviews
  public getAllReviews():  Observable<Review[]> {
    return this.http.get<Review[]>(`${this.host}/review/list`)
  }

  // * Get all reviews by product
  public getAllReviewsByProduct(code : string):  Observable<Review[]> {
    return this.http.get<Review[]>(`${this.host}/review/product/${code}`)
  }
  
  // * Get review by ID
  public getReviewById(id : string) : Observable<Review> {
      return this.http.get<Review>(`${this.host}/review/${id}`)
  }

   // * Crear review
   public createReview(formData: any): Observable<Review> {
    return this.http.post<Review>(`${this.host}/review/create`, formData);
  }

  // * Actualizar review
  public updateReview(id : string , formData: any): Observable<Review> {
    return this.http.put<Review>(`${this.host}/review/update/${id}`, formData);
  }
  
  // * Eliminar review
  public deleteReview(id: string): Observable<Review> {
    return this.http.delete<Review>(`${this.host}/review/${id}`);
  }

}
