import { Injectable } from '@angular/core';

import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { PageParams } from 'src/app/shared/models/PageParams';
import Page from 'src/app/shared/models/Page';
import { BaseDynamicPagingService } from '../base/base-dynamic-paging.service';

@Injectable({
  providedIn: 'root',
})
export class OfferService extends BaseDynamicPagingService {
  constructor(public http: HttpClient) {
    super('cultural_offers', http);
  }

  createEmpty(): CulturalOffer {
    return {
      id: 0,
      name: '',
      description: '',
      latitude: 0,
      longitude: 0,
      city: '',
      address: '',
      region: '',
      admin: 0,
      category: 0,
      categoryName: '',
      averageRating: 0,
      reviews: [],
      posts: [],
      pictures: [],
    };
  }

  searchFilterCulturalOffers(
    pageParams: PageParams,
    categoryName: string,
    query: string,
    regionNames: string,
    cityNames: string
  ): Observable<Page<CulturalOffer>> {
    const params = new HttpParams()
      .set('pageNo', pageParams.pageNo.toString())
      .set('pageSize', pageParams.pageSize.toString())
      .set('sortBy', pageParams.sortBy)
      .set('descending', pageParams.descending.toString())
      .set('categoryName', categoryName)
      .set('query', query)
      .set('regionNames', regionNames)
      .set('cityNames', cityNames);
    const options = { ...this.httpOptions, params };
    return this.http
      .get<Page<CulturalOffer>>(this.url + '/search', options)
      .pipe(catchError(this.handleError<Page<CulturalOffer>>()));
  }
}
