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

  private readonly endpoint = 'http://localhost:9001/cultural_offers/search';

  constructor(private httpClient: HttpClient) { }

  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
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
    return this.httpClient.get<CulturalOfferPage>(this.endpoint , options)
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
