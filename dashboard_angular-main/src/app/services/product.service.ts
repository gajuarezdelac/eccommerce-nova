import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pagination } from '../models/Pagination';
import { Product } from '../models/Product';
import { ProductPaginate } from '../models/ProductPaginate';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public host  = environment.apiUrl;

  constructor(private http: HttpClient) { }
  
   // * Get all products
  public getAllProducts():  Observable<Product[]> {
    return this.http.get<Product[]>(`${this.host}/product/list`)
  }

  
  // * Get list of inbox
  public getAllProductsPaginate(pagination : Pagination):  Observable<ProductPaginate> {

    const params = new HttpParams({
      fromObject: {
        pageNo: pagination.numberPage,
        pageSize: pagination.sizePage
      }
    });

    return this.http.get<ProductPaginate>(`${this.host}/product/paginate`,{ params: params } )
  }




  // * Buscar listado de productos por código
  public getAllProductsByCode(code : string):  Observable<Product[]> {
    return this.http.get<Product[]>(`${this.host}/product/${code}`)
  }

  // * Get product by ID
  public getProductById(id : string) : Observable<Product> {
      return this.http.get<Product>(`${this.host}/product/${id}`)
  }

   // * Crear producto
   public createProduct(formData: any): Observable<Product> {
    return this.http.post<Product>(`${this.host}/product/add`, formData);
  }

  // * Actualizar producto por ID
  public updateProduct(id : string , formData: any): Observable<Product> {
    return this.http.put<Product>(`${this.host}/product/update/${id}`, formData);
  }
  
  // * Eliminar review
  public deleteProduct(id: string): Observable<Product> {
    return this.http.delete<Product>(`${this.host}/product/${id}`);
  }

}
