import { BaseService } from 'src/app/services/base/base.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { Observable } from 'rxjs/Rx';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService extends BaseService {
  
  constructor(public http: HttpClient) {
    super('subcategories', http);
   }

   createEmpty(): Subcategory {
    return {
      id: 0,
      name: '',
      categoryId: 0,
      categoryName: '',
      icon: {
        id: 0,
        placeholder: '',
        image: '',
        path: ''
      },
      containsOffers: false
    }
  }

  addMultipart(object: Subcategory, files: FileList): Observable<Subcategory> {
    const formData = new FormData();
    for (let i = 0; i < files?.length || 0; i++)
      formData.append('files', files[i]);

    formData.append('subcat', JSON.stringify(object));

    return this.http
      .post<Subcategory>(this.url, formData)
      .pipe(catchError(this.handleError<Subcategory>()));
  }
}
