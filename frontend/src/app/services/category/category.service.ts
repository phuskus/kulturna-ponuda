import { Injectable } from '@angular/core';

import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Category } from 'src/app/model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  endpoint = 'http://localhost:9001';
  
  constructor(private httpClient: HttpClient) { }

  token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImFkbWluMkBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTUwNTUsImV4cCI6MTYxMDkzODI1NX0.C7pHXMr_YODv5ED6Z0RzOkebPWakBvsT0fRQS0bDguYpN_TTZRNAvSfyWo-62EwiQYvL4vrGsucELs4bQzCytQ";

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
