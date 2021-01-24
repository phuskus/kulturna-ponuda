import { DeleteSubcategoryDialogComponent } from './../subcategory-dialogs/delete-subcategory-dialog/delete-subcategory-dialog.component';
import { UpdateSubcategoryDialogComponent } from './../subcategory-dialogs/update-subcategory-dialog/update-subcategory-dialog.component';
import { AddSubcategoryDialogComponent } from './../../../../../src/app/modules/admin/components/subcategories/subcategory-dialogs/add-subcategory-dialog/add-subcategory-dialog.component';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';

@Component({
  selector: 'app-subcategory-table',
  templateUrl: './subcategory-table.component.html',
  styleUrls: ['./subcategory-table.component.scss'],
})
export class SubcategoryTableComponent extends AbstractDynamicPagingTable {
  constructor(service: SubcategoryService, public dialog: MatDialog) {
    super(service, dialog);
    this.tableColumns = ['name', 'category', 'icon', 'actions'];
  }

  openAddDialog(): void {
    this.openDialog(AddSubcategoryDialogComponent);
  }

  openUpdateDialog(row: Subcategory): void {
    this.openDialog(UpdateSubcategoryDialogComponent, row);
  }

  openDeleteDialog(row: Subcategory): void {
    this.openDialog(DeleteSubcategoryDialogComponent, row);
  }
}
