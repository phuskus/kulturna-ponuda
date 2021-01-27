import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AbstractTable } from './AbstractTable';
import { merge, Observable, of as observableOf, pipe } from 'rxjs';
import { catchError, delay, map, startWith, switchMap } from 'rxjs/operators';
import { BaseDynamicPagingService } from 'src/app/services/base/base-dynamic-paging.service';
import Model from 'src/app/shared/models/Model';
import { MatDialog } from '@angular/material/dialog';
import Page from 'src/app/shared/models/Page';

@Component({
  template: '',
})
export class AbstractDynamicPagingTable extends AbstractTable {
  timer = null;
  isLoadingResults: boolean = false;
  filter: string = '';
  resultsLength = 0;

  constructor(public service: BaseDynamicPagingService, dialog: MatDialog) {
    super(service, dialog);
  }

  ngAfterViewInit(): void {
    // subscribe to sort and paginator changes to fetch data
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(this.getDataPipeline())
      .subscribe((data) => {
        this.dataSource.data = data;
      });
  }

  updateTable(): void {
    // simulate sort event so the table refreshes its data
    this.sort.sortChange.emit();
  }

  getDataPipeline() {
    return pipe(
      startWith({}),
      switchMap(() => this.fetchData()),
      map((data) => this.collectFetchedData(data)),
      catchError(() => this.handleError())
    );
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
    clearTimeout(this.timer);
    this.timer = setTimeout(() => {
      const filterValue = (event.target as HTMLInputElement).value;
      this.filter = filterValue.trim().toLowerCase();
      this.updateTable();
    }, 500);
  }
}
