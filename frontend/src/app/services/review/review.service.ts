import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, delay } from 'rxjs/operators';
import Page from 'src/app/shared/models/Page';
import { Review } from '../../shared/models/Review';
import { BaseDynamicPagingService } from '../base/base-dynamic-paging.service';

@Injectable({
  providedIn: 'root',
})
export class ReviewService extends BaseDynamicPagingService {
  constructor(public http: HttpClient) {
    super('reviews', http);
  }

  createEmpty(): Review {
    return {
      id: 392,
      rating: 1,
      content:
        'Et consequat sunt sit irure culpa non amet ad et officia. Magna ipsum consectetur qui exercitation culpa ut cupidatat culpa proident in minim minim incididunt. Cupidatat ut eu ipsum do ullamco irure eu do aliquip aute id ut.\r\n',
      user: {
        id: 279,
        name: 'Celia',
        surname: 'James',
        username: 'Marian',
        password: '',
      },
      culturalOfferId: 314,
      culturalOfferName: 'Acruex',
      pictures: [],
    };
  }

  getForOfferId(
    id: number,
    pageNumber: number,
    pageSize: number
  ): Observable<Page<Review>> {
    const delayInMiliseconds = pageNumber > 0 ? 500: 0;
    return this.http
      .get<Page<Review>>(
        `${this.url}/offer/${id}?pageNo=${pageNumber}&pageSize=${pageSize}`,
        this.httpOptions
      )
      .pipe(catchError(this.handleError<Page<Review>>()), delay(delayInMiliseconds));
  }
}
