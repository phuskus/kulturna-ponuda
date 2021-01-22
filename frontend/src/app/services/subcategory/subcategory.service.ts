import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Model from 'src/app/shared/models/Model';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { BaseService } from '../base/base.service';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService extends BaseService {
  
  constructor(public http: HttpClient) {
    super('http://localhost:9001/subcategories', http);
   }

   createEmpty(): Subcategory {
    return {
      id: 0,
      name: '',
      categoryId: 0,
      icon: {
        id: 0,
        placeholder: '',
        image: '',
        path: ''
      },
      containsOffers: false
    }
  }
}
