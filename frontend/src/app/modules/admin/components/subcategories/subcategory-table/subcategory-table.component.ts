import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import { AddSubcategoryDialogComponent } from '../subcategory-dialogs/add-subcategory-dialog/add-subcategory-dialog.component';
import { DeleteSubcategoryDialogComponent } from '../subcategory-dialogs/delete-subcategory-dialog/delete-subcategory-dialog.component';
import { UpdateSubcategoryDialogComponent } from '../subcategory-dialogs/update-subcategory-dialog/update-subcategory-dialog.component';
import { AbstractTable } from '../../table/AbstractTable';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';

@Component({
  selector: 'app-subcategory-table',
  templateUrl: './subcategory-table.component.html',
  styleUrls: ['./subcategory-table.component.scss'],
})
export class SubcategoryTableComponent extends AbstractTable {
  constructor(service: SubcategoryService, public dialog: MatDialog, public pathExtractionService: PathExtractionService) {
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
