import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared/models/User';
import { BaseService } from '../base/base.service';

@Injectable({
  providedIn: 'root',
})
export class UserService extends BaseService {
  constructor(public http: HttpClient) {
    super('auth/account', http);
  }

  getLoggedUser(): Observable<User> {
    let currentUser = JSON.parse(localStorage['currentUser']);
    return this.get(currentUser.id) as Observable<User>;
  }

  isUserLoggedIn(): boolean {
    return localStorage['currentUser'] !== undefined;
  }

  createEmpty(): User {
    return {
      id: -1,
      name: '',
      surname: '',
      username: '',
      password: '',
    };
  }
}
