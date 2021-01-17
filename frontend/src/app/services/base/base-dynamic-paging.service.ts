import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseDynamicPagingService<T> extends BaseService<T> {
  constructor() {
    super();
  }

  abstract getPage(
    pageNumber: number,
    pageSize: number,
    sortBy: string,
    descending: boolean
  ): Observable<PagingReturnValue<T>>;

  getAll(): Observable<T[]> {
    throw new Error('Dyanmic Paging does not implement method getAll');
  }
}

export default interface PagingReturnValue<T> {
  items: T[];
  total_count: number;
}
