import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from '../models/Product';

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

  // * Buscar listado de productos por código
  public getAllProductsByCode(code : string):  Observable<Product[]> {
    return this.http.get<Product[]>(`${this.host}/product/${code}`)
  }

  // * Traer listado de productos paginados
  public getAllProductPaginate(code : string):  Observable<Product[]> {
    return this.http.get<Product[]>(`${this.host}/product/paginate`)
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
  public eliminarProduct(id: string): Observable<Product> {
    return this.http.delete<Product>(`${this.host}/product/${id}`);
  }

}
