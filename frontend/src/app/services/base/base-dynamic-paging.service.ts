import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Model from 'src/app/shared/models/Model';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseDynamicPagingService extends BaseService {
  constructor() {
    super();
  }

  abstract getPage(
    pageNumber: number,
    pageSize: number,
    sortBy: string,
    descending: boolean
  ): Observable<PagingReturnValue<Model>>;

  getAll(): Observable<Model[]> {
    throw new Error('Dyanmic Paging does not implement method getAll');
  }
}

export default interface PagingReturnValue<Model> {
  items: Model[];
  total_count: number;
}
