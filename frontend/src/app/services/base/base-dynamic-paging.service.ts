import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, delay } from 'rxjs/operators';
import Model from 'src/app/shared/models/Model';
import Page from 'src/app/shared/models/Page';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseDynamicPagingService extends BaseService {
  constructor(public path: string, public http: HttpClient) {
    super(path, http);
  }

  getPage(
    pageNumber: number,
    pageSize: number,
    sortBy: string,
    descending: boolean
  ): Observable<Page<Model>> {
    return this.http
      .get<Page<Model>>(
        `${this.url}?pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${
          sortBy || 'id'
        }&descending=${descending}`,
        this.httpOptions
      )
      .pipe(catchError(this.handleError<Page<Model>>()), delay(250));
  }

  search(
    query: string,
    pageNumber: number,
    pageSize: number,
    sortBy: string,
    descending: boolean
  ): Observable<Page<Model>> {
    return this.http
      .get<Page<Model>>(
        `${
          this.url
        }/search?query=${query}&pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${
          sortBy || 'id'
        }&descending=${descending}`,
        this.httpOptions
      )
      .pipe(catchError(this.handleError<Page<Model>>()), delay(250));
  }

  getAll(): Observable<Model[]> {
    throw new Error('Dyanmic Paging does not implement method getAll');
  }
}
