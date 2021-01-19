import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AbstractTable } from './AbstractTable';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, delay, map, startWith, switchMap } from 'rxjs/operators';
import PagingReturnValue, {
  BaseDynamicPagingService,
} from 'src/app/services/base/base-dynamic-paging.service';
import Model from 'src/app/shared/models/Model';
import { MatDialog } from '@angular/material/dialog';
import * as _ from 'underscore';

@Component({
  template: '',
})
export abstract class AbstractDynamicPagingTable extends AbstractTable {
  isLoadingResults: boolean = false;
  filter: string = '';
  resultsLength = 0;
  service: BaseDynamicPagingService;

  constructor(service: BaseDynamicPagingService, dialog: MatDialog) {
    super(service, dialog);
  }

  subscribe(): void {
    console.log('Abstract Paging Table Subscribe called');

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => this.fetchData()),
        delay(1000),
        map((data) => this.collectFetchedData(data)),
        catchError(() => this.handleError())
      )
      .subscribe((data) => {
        this.dataSource.data = data;
      });
  }

  fetchData(): Observable<PagingReturnValue<Model>> {
    this.isLoadingResults = true;
    let isDescending: boolean = this.sort.direction === 'desc';
    if (this.filter === '') return this.getPage(isDescending);

    return this.search(isDescending);
  }

  getPage(isDescending: boolean): Observable<PagingReturnValue<Model>> {
    return this.service.getPage(
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sort.active,
      isDescending
    );
  }

  search(isDescending: boolean): Observable<PagingReturnValue<Model>> {
    return this.service.search(
      this.filter,
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sort.active,
      isDescending
    );
  }

  collectFetchedData(data: PagingReturnValue<Model>): Model[] {
    this.isLoadingResults = false;
    this.resultsLength = data.total_count;
    return data.items;
  }

  handleError() {
    this.isLoadingResults = false;
    return observableOf([]);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();
    this.subscribe();

    // _.debounce({this.fn(event)}, 2500);
  }
}
