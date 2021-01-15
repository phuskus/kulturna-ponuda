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

  getToken(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  logout(): void {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    if (this.getToken() != '') return true;
    else return false;
  }

  getCurrentUser() {
    if (localStorage.currentUser) {
      return JSON.parse(localStorage.currentUser);
    }
    else {
      return undefined;
    }
  }

}
