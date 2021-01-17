import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import { Admin } from 'src/app/shared/models/Admin';
import { AbstractTable } from '../../table/AbstractTable';
import { AddAdminDialogComponent } from '../admin-dialogs/add-admin-dialog/add-admin-dialog.component';
import { DeleteAdminDialogComponent } from '../admin-dialogs/delete-admin-dialog/delete-admin-dialog.component';

@Component({
  selector: 'app-admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss'],
})
export class AdminTableComponent extends AbstractTable {
  constructor(service: AdminService, dialog: MatDialog) {
    super(service, dialog);
    this.tableColumns = ['name', 'username', 'actions'];
  }

  openAddDialog(): void {
    this.openDialog(AddAdminDialogComponent, {id: null});
  }

  openDeleteDialog(row: Admin): void {
    this.openDialog(DeleteAdminDialogComponent, row);
  }
}
