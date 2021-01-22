import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from '../base/base.service';
import { Category } from 'src/app/shared/models/Category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService extends BaseService {
  constructor(public http: HttpClient) {
    super('http://localhost:9001/categories', http);
  }

  createEmpty(): Category {
    return {
      id: 0,
      name: '',
      subcategories: [],
    };
  }
}
