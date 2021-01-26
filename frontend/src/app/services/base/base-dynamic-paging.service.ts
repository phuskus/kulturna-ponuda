import { HttpClient } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, delay } from 'rxjs/operators';
import Model from 'src/app/shared/models/Model';
import Page from 'src/app/shared/models/Page';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseDynamicPagingService extends BaseService {
  constructor(public http: HttpClient, @Optional() public path?: string) {
    super(http, path);
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
      .pipe(catchError(this.handleError<Page<Model>>('getPage')), delay(250));
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
      .pipe(catchError(this.handleError<Page<Model>>('search')), delay(250));
  }

  getAll(): Observable<Model[]> {
    throw new Error('Dynamic Paging does not implement method getAll');
  }
}
