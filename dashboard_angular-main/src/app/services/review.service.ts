import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pagination } from '../models/Pagination';
import { Review, Content } from '../models/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  
  public host  = environment.apiUrl;

  constructor(private http: HttpClient) { }

  // * Get all reviews
  public getAllReviews():  Observable<Content[]> {
    return this.http.get<Content[]>(`${this.host}/review/list`)
  }


  // * Get list of reviews paginate
  public getAllReviewsPaginate(pagination : Pagination):  Observable<Review> {

    const params = new HttpParams({
      fromObject: {
        pageNo: pagination.numberPage,
        pageSize: pagination.sizePage
      }
    });

    return this.http.get<Review>(`${this.host}/review/paginate`,{ params: params } )
  }

  // * Get all reviews by product
  public getAllReviewsByProduct(code : string):  Observable<Content[]> {
    return this.http.get<Content[]>(`${this.host}/review/product/${code}`)
  }
  
  // * Get review by ID
  public getReviewById(id : string) : Observable<Content> {
      return this.http.get<Content>(`${this.host}/review/${id}`)
  }

   // * Crear review
   public createReview(formData: any): Observable<Content> {
    return this.http.post<Content>(`${this.host}/review/create`, formData);
  }

  // * Actualizar review
  public updateReview(id : string , formData: any): Observable<Content> {
    return this.http.put<Content>(`${this.host}/review/update/${id}`, formData);
  }
  
  // * Eliminar review
  public deleteReview(id: string): Observable<Content> {
    return this.http.delete<Content>(`${this.host}/review/${id}`);
  }

}
