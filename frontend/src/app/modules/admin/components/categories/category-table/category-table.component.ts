import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category/category.service';
import { Category } from 'src/app/shared/models/Category';
import { AbstractTable } from '../../table/AbstractTable';
import { AddCategoryDialogComponent } from '../category-dialogs/add-category-dialog/add-category-dialog.component';
import { DeleteCategoryDialogComponent } from '../category-dialogs/delete-category-dialog/delete-category-dialog.component';
import { UpdateCategoryDialogComponent } from '../category-dialogs/update-category-dialog/update-category-dialog.component';

@Component({
  selector: 'app-category-table',
  templateUrl: './category-table.component.html',
  styleUrls: ['./category-table.component.scss'],
})
export class CategoryTableComponent extends AbstractTable {
  constructor(service: CategoryService, dialog: MatDialog) {
    super(service, dialog);
    this.tableColumns = ['name', 'subcategories', 'actions'];
  }

  openAddDialog(): void {
    this.openDialog(AddCategoryDialogComponent);
  }

  openUpdateDialog(row: Category): void {
    this.openDialog(UpdateCategoryDialogComponent, row);
  }

  openDeleteDialog(row: Category): void {
    this.openDialog(DeleteCategoryDialogComponent, row);
  }

  getSubcategoryNames(category: Category) {
    return category.subcategories
      .map((sub) => sub.name)
      .join(', ')
      .substring(0, 50);
  }
}
