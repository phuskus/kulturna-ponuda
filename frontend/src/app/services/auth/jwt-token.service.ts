import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtTokenService {

  constructor() { }

  getToken(): string {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var token = currentUser && currentUser.token;
    return token ? token : "";
  }

  getExpirationDate(): string {
    var currentUser = JSON.parse(localStorage.getItem('currentUser'));
    var expDate = currentUser && currentUser.expiresIn;
    return expDate ? expDate : "";

  }

  checkToken(): boolean {
    if (this.getToken() != '') {
      return true;
    } else {
      return false;
    }
  }

}
