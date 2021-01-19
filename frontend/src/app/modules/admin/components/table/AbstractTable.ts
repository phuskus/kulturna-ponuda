import { ComponentType } from '@angular/cdk/portal';
import {
  AfterContentInit,
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { BaseService } from 'src/app/services/base/base.service';
import Dialog from 'src/app/shared/dialog/Dialog';
import Model from 'src/app/shared/models/Model';

@Component({
  template: '',
})
export abstract class AbstractTable implements AfterViewInit {
  dataSource: MatTableDataSource<Model> = new MatTableDataSource();
  tableColumns: string[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  constructor(public service: BaseService, public dialog: MatDialog) {}

  ngAfterViewInit(): void {
    // when sort is changed go back to first page
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
    this.getTableData();
  }

  private getTableData(): void {
    this.service.getAll().subscribe((data) => {
      this.dataSource.data = data;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  openDialog(type: ComponentType<unknown>, row?: Model): void {
    const dialogRef = this.dialog.open(type, {
      data: row,
    });

    const sub = (dialogRef.componentInstance as Dialog<Model>).onSubscriptionCallBack.subscribe((data) => {
      this.getTableData();

      // since dialog is now closed, you can unsubscribe from its events
      sub.unsubscribe();
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  add(object: Model): void {
    this.service.add(object);
  }

  update(id: number, object: Model): void {
    this.service.update(id, object);
  }

  delete(id: number): void {
    this.service.delete(id);
  }
}
