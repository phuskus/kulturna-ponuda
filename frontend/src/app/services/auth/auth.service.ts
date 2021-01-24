import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs'; //npm install --save rxjs-compat
import { catchError } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { JwtTokenService } from './jwt-token.service';
import { UserTokenState } from 'src/app/shared/models/UserTokenState';
import { Role } from 'src/app/shared/models/Role';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private endpoint = "http://localhost:9001/auth/";
  //private readonly loginEndpoint = "http://localhost:9001/auth/login";
  //private readonly registerEndpoint = "http://localhost:9001/auth/register";
  //private readonly forgotPasswordEndpoint = "http://localhost:9001/auth/forgot-password";
  //private readonly resetPasswordEndpoint = "http://localhost:9001/auth/reset-password"; 

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.endpoint + 'login',
      JSON.stringify({ username, password }),
      { headers }).map((res: any) => {
        let user: UserTokenState =  {
          id: res && res['id'],
          token: res && res['accessToken'],
          expiresIn: res && res['expiresIn'],
          userRole: res && res['userRole']
        };
        if (user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          console.log(localStorage['currentUser']);
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
      this.endpoint + 'register',
      JSON.stringify({name, surname, username, password }),
      { headers })
      .pipe(
        catchError(error => {
          return throwError(error.error);
        })
      );
  }

  changePassword(oldPassword: string, newPassword: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.endpoint + 'change-password',
      JSON.stringify({oldPassword, newPassword}),
      { headers })
      .pipe(
        catchError(error => {
          return throwError(error.error);
        })
      );
  }

  activateAccount(key: string): Observable<any> {
    return this.http.get(
      this.endpoint + "register/" + key,
    ).pipe(
      catchError(error => {
        return throwError(error);
      })
    )
  }

  forgotPassword(username: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'text/plain' });
    return this.http.post(
      this.endpoint + 'forgot-password',
      username,
      { headers }
    ).pipe(
      catchError(error => {
        return throwError(error);
      })
    )
  }

  resetPassword(newPassword: string, resetKey: string) {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.endpoint + 'reset-password',
      JSON.stringify({newPassword, resetKey}),
      { headers })
      .map((res: any) => {
        let user: UserTokenState =  {
          id: res && res['id'],
          token: res && res['accessToken'],
          expiresIn: res && res['expiresIn'],
          userRole: res && res['userRole']
        };
        localStorage.setItem('currentUser', JSON.stringify(user));
        console.log(localStorage['currentUser']);
      }
      ).pipe(
      catchError(error => {
        return throwError(error);
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

  getCurrentUserRole(): Role {
    let user = localStorage.currentUser;
    if (user) {
      user = JSON.parse(user);
      let role: string = user.userRole;
      return Role[role];
    }
    else {
      return Role['NO_AUTH'];
    }
  }


}
