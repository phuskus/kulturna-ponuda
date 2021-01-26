import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BaseService } from '../base/base.service';
import { Category } from 'src/app/shared/models/Category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService extends BaseService {
  constructor(public http: HttpClient) {
    super(http, 'categories');
  }

  createEmpty(): Category {
    return {
      id: 0,
      name: '',
      subcategories: [],
    };
  }
}
