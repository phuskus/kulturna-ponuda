import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Admin } from 'src/app/shared/models/Admin';
import { BaseService } from '../base/base.service';

@Injectable({
  providedIn: 'root',
})
export class AdminService extends BaseService {
  constructor(public http: HttpClient) {
    super("admins", http);
  }

  createEmpty(): Admin {
    return {
      id: 0,
      name: '',
      surname: '',
      username: '',
      password: '',
    };
  }
}