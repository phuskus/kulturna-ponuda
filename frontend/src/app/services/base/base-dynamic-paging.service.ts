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
    pageIndex: number,
    sortBy: string,
    descending: boolean
  ): PagingReturnValue<T>;

  getAll(): Observable<T[]> {
    throw new Error('Dyanmic Paging does not implement method getAll');
  }
}

export default interface PagingReturnValue<T> {
  items: Observable<T[]>;
  total_count: number;
}
