import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { BaseService } from 'src/app/services/base/base.service';
import { AbstractTable } from './AbstractTable';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { BaseDynamicPagingService } from 'src/app/services/base/base-dynamic-paging.service';

@Component({
  template: '',
})
export abstract class AbstractDynamicPagingTable<T> extends AbstractTable<T> {
  isLoadingResults: boolean = false;
  service: BaseDynamicPagingService<T>;

  constructor(service: BaseDynamicPagingService<T>) {
    super(service);
  }

  subscribe(): void {
    console.log('Abstract Paging Table Subscribe called');
    this.paginator.page
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.service.getPage(this.paginator.pageIndex, '', false);
        }),
        map((data) => {
          this.isLoadingResults = false;
          this.resultsLength = data.total_count;

          return data.items;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          return observableOf([]);
        })
      )
      .subscribe((data) => {
        console.log(data)
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }
}
