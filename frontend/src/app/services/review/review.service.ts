import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
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
      id: -1,
      rating: 0,
      content: '',
      user: {
        id: 11,
        name: 'Justin',
        surname: 'Block',
        username: 'lorenza.labadie@hotmail.com',
        password: '',
      },
      culturalOfferId: -1,
      culturalOfferName: '',
      pictures: [],
    };
  }

  getForOfferId(
    id: number,
    pageNumber: number,
    pageSize: number
  ): Observable<Page<Review>> {
    const delayInMiliseconds = pageNumber > 0 ? 500 : 0;
    return this.http
      .get<Page<Review>>(
        `${this.url}/offer/${id}?pageNo=${pageNumber}&pageSize=${pageSize}`,
        this.httpOptions
      )
      .pipe(
        catchError(this.handleError<Page<Review>>()),
        delay(delayInMiliseconds)
      );
  }

  addMultipart(review: Review, files: FileList): Observable<Review> {
    const formData = new FormData();
    for (let i = 0; i < files?.length || 0; i++)
      formData.append('files', files[i]);

    formData.append('review', JSON.stringify(review));

    return this.http
      .post<Review>(this.url, formData)
      .pipe(catchError(this.handleError<Review>()));
  }
}
