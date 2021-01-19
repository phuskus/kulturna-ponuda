import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtTokenService {

  constructor() { }

  getToken(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  getExpirationDate(): String {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var expDate = currentUser && currentUser.expiresIn;
    return expDate ? expDate : "";

  }

  checkToken(): boolean {
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
}
