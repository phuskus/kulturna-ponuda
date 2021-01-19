import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs'; //npm install --save rxjs-compat
import { catchError } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { JwtTokenService } from './jwt-token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly loginEndpoint = "http://localhost:9001/auth/login";
  private readonly registerEndpoint = "http://localhost:9001/auth/register";
  private readonly activationEndpoint = "http://localhost:9001/auth/register/";

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.loginEndpoint,
      JSON.stringify({ username, password }),
      { headers }).map((res: any) => {
        let token = res && res['accessToken'];
        let expiresIn = res && res['expiresIn'];
        let userRole = res && res['userRole'];
        if (token) {
          localStorage.setItem('currentUser', JSON.stringify({
            username: username,
            token: token,
            expiresIn: expiresIn,
            role: userRole
          }));
          return true;
        }
      }).pipe(
        catchError(error => {
          return throwError(error);
        })
      );
  }

  register(name: string, surname: string, username: string, password: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.registerEndpoint,
      JSON.stringify({name, surname, username, password }),
      { headers })
      .pipe(
        catchError(error => {
          return throwError(error.error);
        })
      );
  }

  activateAccount(key: string): Observable<any> {
    return this.http.get(
      this.activationEndpoint + key,
    ).pipe(
      catchError(error => {
        return throwError(error.error);
      })
    )
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    }
    else {
      return undefined;
    }
  }

  getCurrentUserRole() {
    let user = localStorage.currentUser;
    if (user) {
      user = JSON.parse(user);
      return user.role;
    }
    else {
      return "NO_AUTH";
    }
  }


}
