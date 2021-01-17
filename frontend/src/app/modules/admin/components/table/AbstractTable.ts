import {
  AfterContentInit,
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BaseService } from 'src/app/services/base/base.service';


@Component({
  template: '',
})
export abstract class AbstractTable<T>
  implements AfterViewInit {
  dataSource: MatTableDataSource<T> = new MatTableDataSource();
  displayedColumns: string[];

  resultsLength = 0;
  shouldSubscibe: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public service: BaseService<T>) {}

  ngAfterViewInit(): void {
    this.subscribe();
  }

  subscribe(): void {
    this.service.getAll().subscribe((data) => {
      this.dataSource.data = data;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
