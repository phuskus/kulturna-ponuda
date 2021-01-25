import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Page from 'src/app/shared/models/Page';
import { Post } from 'src/app/shared/models/Post';
import { BaseDynamicPagingService } from '../base/base-dynamic-paging.service';

@Injectable({
  providedIn: 'root',
})
export class PostService extends BaseDynamicPagingService {
  constructor(public http: HttpClient) {
    super('posts', http);
  }

  createEmpty(): Post {
    return {
      id: -1,
      title: '',
      content: '',
      culturalOffer: -1,
      offerName: '',
      datePosted: new Date(),
      pictures: []
    }
  }

  getForOfferId(
    id: number,
    pageNumber: number,
    pageSize: number
  ): Observable<Page<Post>> {
    return this.http
      .get<Page<Post>>(
        `${this.url}/offer/${id}?pageNo=${pageNumber}&pageSize=${pageSize}`,
        this.httpOptions
      )
      .pipe(
        catchError(this.handleError<Page<Post>>()),
      );
  }

  addMultipart(post: Post, files: FileList): Observable<Post> {
    const formData = new FormData();
    for (let i = 0; i < files?.length || 0; i++)
      formData.append('files', files[i]);

    formData.append('post', JSON.stringify(post));

    return this.http
      .post<Post>(this.url, formData)
      .pipe(catchError(this.handleError<Post>()));
  }

  
}
