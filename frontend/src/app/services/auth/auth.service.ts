import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs'; //npm install --save rxjs-compat
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private readonly endpoint = "http://localhost:9001/auth/login";
  
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<boolean> {
    var headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(
      this.endpoint,
      JSON.stringify({username, password}),
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
        else {
          return false;
        }
      }) // error?
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  getToken(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  getExpirationDate(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var expDate = currentUser && currentUser.expiresIn;
    return expDate ? expDate: "";
    
  }

  isLoggedIn(): boolean {
    if (this.getToken() != '') {
      return true;
    } else if (!this.isTokenExpired()) {
      return true;
    } else {
      return false;
    }
  }

  isTokenExpired(): boolean {
    let exp = this.getExpirationDate();
    if (exp != '') {
      if (Date.now() >= +exp * 1000) {
        return false;
      }
    } else {
      return true;
    }
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
    let currentUser = localStorage.currentUser;
    if (currentUser) {
      return currentUser.userRole;
    }
    else {
      return undefined;
    }
  }

}
