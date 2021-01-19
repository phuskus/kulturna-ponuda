import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs'; //npm install --save rxjs-compat
import { catchError } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { JwtTokenService } from './jwt-token.service';
import { UserTokenState } from 'src/app/shared/models/UserTokenState';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly loginEndpoint = "http://localhost:9001/auth/login";
  private readonly registerEndpoint = "http://localhost:9001/auth/register";
  private readonly forgotPasswordEndpoint = "http://localhost:9001/auth/forgot-password";
  private readonly resetPasswordEndpoint = "http://localhost:9001/auth/reset-password"; 

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.loginEndpoint,
      JSON.stringify({ username, password }),
      { headers }).map((res: any) => {
        let user: UserTokenState =  {
          token: res && res['accessToken'],
          expiresIn: res && res['expiresIn'],
          userRole: res && res['userRole']
        };
        //let token = res && res['accessToken'];
        //let expiresIn = res && res['expiresIn'];
        //let userRole = res && res['userRole'];
        if (user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user))
            /*username: username,
            token: token,
            expiresIn: expiresIn,
            role: userRole
          }));*/
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
      this.registerEndpoint + "/" + key,
    ).pipe(
      catchError(error => {
        return throwError(error);
      })
    )
  }

  forgotPassword(username: string): Observable<any> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'text/plain' });
    return this.http.post(
      this.forgotPasswordEndpoint,
      username,
      { headers }
    ).map((res: any) => {
      let user: UserTokenState =  {
        token: res && res['accessToken'],
        expiresIn: res && res['expiresIn'],
        userRole: res && res['userRole']
      };
      localStorage.setItem('currentUser', JSON.stringify(user));
    }).pipe(
      catchError(error => {
        return throwError(error);
      })
    )
  }

  resetPassword(newPassword: string, resetKey: string) {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'text/plain' });
    return this.http.post(
      this.resetPasswordEndpoint,
      JSON.stringify({newPassword, resetKey}),
      { headers }
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
