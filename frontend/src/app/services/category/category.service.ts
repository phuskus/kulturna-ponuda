import { Injectable } from '@angular/core';

import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from 'src/app/model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  endpoint = 'http://localhost:9001/categories';
  
  constructor(private httpClient: HttpClient) { }

  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  }  

  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(this.endpoint, this.httpHeaders)
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
