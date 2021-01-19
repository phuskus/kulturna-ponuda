import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AbstractTable } from './AbstractTable';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, delay, map, startWith, switchMap } from 'rxjs/operators';
import { BaseDynamicPagingService } from 'src/app/services/base/base-dynamic-paging.service';
import Model from 'src/app/shared/models/Model';
import { MatDialog } from '@angular/material/dialog';
import * as _ from 'underscore';
import Page from 'src/app/shared/models/Page';

@Component({
  template: '',
})
export abstract class AbstractDynamicPagingTable extends AbstractTable {
  isLoadingResults: boolean = false;
  filter: string = '';
  resultsLength = 0;

  constructor(public service: BaseDynamicPagingService, dialog: MatDialog) {
    super(service, dialog);
  }

  getTableData(): void {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => this.fetchData()),
        // delay(1000),
        map((data) => this.collectFetchedData(data)),
        catchError(() => this.handleError())
      )
      .subscribe((data) => {
        this.dataSource.data = data;
      });
  }

  fetchData(): Observable<Page<Model>> {
    this.isLoadingResults = true;
    let isDescending: boolean = this.sort.direction === 'desc';
    if (this.filter === '') return this.getPage(isDescending);

    return this.search(isDescending);
  }

  getPage(isDescending: boolean): Observable<Page<Model>> {
    return this.service.getPage(
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sort.active,
      isDescending
    );
  }

  search(isDescending: boolean): Observable<Page<Model>> {
    return this.service.search(
      this.filter,
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sort.active,
      isDescending
    );
  }

  collectFetchedData(data: Page<Model>): Model[] {
    this.isLoadingResults = false;
    this.resultsLength = data.totalElements;
    return data.content;
  }

  handleError() {
    this.isLoadingResults = false;
    return observableOf([]);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue.trim().toLowerCase();

    // simulate sort event so the table refreshes its data
    this.sort.sortChange.emit();
  }
}
