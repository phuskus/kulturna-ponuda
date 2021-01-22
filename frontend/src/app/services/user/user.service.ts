import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Model from 'src/app/shared/models/Model';
import { BaseService } from '../base/base.service';

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseService {

  createEmpty(): Model {
    throw new Error('Method not implemented.');
  }

  constructor(public http: HttpClient) {
    super("http://localhost:9001/auth/account", http);
   }
   
}
