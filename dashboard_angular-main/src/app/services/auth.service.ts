import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CustomHttpRespone } from '../models/custom-http-response';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public host  = environment.apiUrl;
  private token: any;
  private loggedInUsername: any;
  private jwtHelper = new JwtHelperService;
  
  constructor(private http: HttpClient) { }

  public login(user: User): Observable<HttpResponse<User>> {
   return this.http.post<User>(`${this.host}/user/login`, user, { observe: 'response' });
 }

 public register(user: User): Observable<User> {
   return this.http.post<User>(`${this.host}/user/register`, user);
 }

  public recoveryPassword(email: string): Observable<CustomHttpRespone> {
    return this.http.get<CustomHttpRespone>(`${this.host}/user/recovery-password/${email}`);
  }

  // * Reset password
  public resetPassword(email: string): Observable<CustomHttpRespone> {
    return this.http.get<CustomHttpRespone>(`${this.host}/user/resetpassword/${email}`);
  }

  public logOut(): void  {
    this.token = null;
    this.loggedInUsername = null;
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    localStorage.removeItem("users");
  }

  public saveToken(token: string) : void {
   
   this.token = token;
   localStorage.setItem("token",token);
  }

  public addUserToLocalCache(user: User) {
   localStorage.setItem('user', JSON.stringify(user));
  } 

  public getUserFromLocalCache() : User {
   // Sintaxis para campos que pueden venir vacios
   return JSON.parse(localStorage.getItem('user') || '{}');
  }

  public loadToken() : void  {
    this.token = localStorage.getItem("token");
  }

  
 public getToken(): string {
   return this.token;
 }


  public isUserLoggedIn(): boolean {
   this.loadToken();
   if (this.token != null && this.token !== ''){
     if (this.jwtHelper.decodeToken(this.token).sub != null || '') {
       if (!this.jwtHelper.isTokenExpired(this.token)) {
         this.loggedInUsername = this.jwtHelper.decodeToken(this.token).sub;
         return true;
       }
     }
   } else {
     this.logOut();
     return false;
   }
   console.log("Pero que ha pasado!!!")
   return false;
 }



}
