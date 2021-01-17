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

  token = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImFkbWluMkBnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTUwNTUsImV4cCI6MTYxMDkzODI1NX0.C7pHXMr_YODv5ED6Z0RzOkebPWakBvsT0fRQS0bDguYpN_TTZRNAvSfyWo-62EwiQYvL4vrGsucELs4bQzCytQ";

  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.token}`
    })
  }


  getCulturalOffersByCategory(category: string, pageParams: PageParams): Observable<CulturalOfferPage> {
    const params = new HttpParams()
      .set('pageNo', pageParams.pageNo.toString())
      .set('pageSize', pageParams.pageSize.toString())
      .set('sortBy', pageParams.sortBy)
      .set('descending', pageParams.descending.toString());
    const options = { ...this.httpHeaders, params };
    return this.httpClient.get<CulturalOfferPage>(this.endpoint + '/cultural_offers/category/' + category, options)
      .pipe(
        retry(1),
        catchError(this.processError)
      )
  }
  getCulturalOffersByQuery(query: string, pageParams: PageParams): Observable<CulturalOfferPage> {
    const params = new HttpParams()
      .set('pageNo', pageParams.pageNo.toString())
      .set('pageSize', pageParams.pageSize.toString())
      .set('sortBy', pageParams.sortBy)
      .set('descending', pageParams.descending.toString());
    const options = { ...this.httpHeaders, params };
    return this.httpClient.get<CulturalOfferPage>(this.endpoint + '/cultural_offers/query/' + query, options)
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
