import { Injectable } from '@angular/core';

import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CulturalOffer } from 'src/app/model/CulturalOffer';
import { PageParams } from 'src/app/model/PageParams';
import { CulturalOfferPage } from 'src/app/model/CulturalOfferPage';




@Injectable({
  providedIn: 'root'
})
export class OfferService {

  endpoint = 'http://localhost:9001';

  constructor(private httpClient: HttpClient) { }

  token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImFkbWluMkBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA5OTQyMjAsImV4cCI6MTYxMTAzNzQyMH0.TY-WDqhfL9KxJvKHnBL5iVGLX0FMKb2NzvFY22gNjDCs2qZCi3BCAqdlDumtGYtWc5Vi_aJdb4gnXOldoX9o2Q";

  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  }


  getCulturalOffers(pageParams: PageParams, categoryName: string, query: string, regionNames: string, cityNames: string): Observable<CulturalOfferPage> {
    const params = new HttpParams()
      .set('pageNo', pageParams.pageNo.toString())
      .set('pageSize', pageParams.pageSize.toString())
      .set('sortBy', pageParams.sortBy)
      .set('descending', pageParams.descending.toString())
      .set('categoryName', categoryName)
      .set('query', query)
      .set('regionNames', regionNames)
      .set('cityNames', cityNames);
    const options = { ...this.httpHeaders, params };
    return this.httpClient.get<CulturalOfferPage>(this.endpoint + '/cultural_offers/search', options)
      .pipe(
        retry(1),
        catchError(this.processError)
      )
  }

  processError(err) {
    let message = '';
    if (err.error instanceof ErrorEvent) {
      message = err.error.message;
    } else {
      message = `Error Code: ${err.status}\nMessage: ${err.message}`;
    }
    console.log(message);
    return throwError(message);
  }

}
