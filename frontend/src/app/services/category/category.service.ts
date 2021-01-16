import { Injectable } from '@angular/core';

import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export class Category {
  id: number;
  name: string;
  subcategories: Subcategory[];
}

export class Subcategory {
  id: number;
  name: string;
  categoryId: number;
  icon: Picture;
}

export class Picture {
  id: number;
  placeholder: string;
  image: string;
  path: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  endpoint = 'http://localhost:9001';
  
  constructor(private httpClient: HttpClient) { }

  token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImFkbWluMkBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA3MTY0NzIsImV4cCI6MTYxMDc1OTY3Mn0.VVZZqnoRIw_B-EN8gk9pPogs9X45GADj6dRHRdFLCSErvG2MRBhRWvQ1VBCeuuJV0kqVLOuwEgtrLgx15XAmJQ";

  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  }  

  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.endpoint + '/categories', this.httpHeaders)
    .pipe(
      retry(1),
      catchError(this.processError)
    )
  }

  processError(err) {
    let message = '';
    if(err.error instanceof ErrorEvent) {
     message = err.error.message;
    } else {
     message = `Error Code: ${err.status}\nMessage: ${err.message}`;
    }
    console.log(message);
    return throwError(message);
 }

}
