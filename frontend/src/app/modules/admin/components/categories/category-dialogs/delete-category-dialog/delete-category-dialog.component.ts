import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category/category.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { Category } from 'src/app/shared/models/Category';

@Component({
  selector: 'app-delete-category-dialog',
  templateUrl: './delete-category-dialog.component.html',
  styleUrls: ['./delete-category-dialog.component.scss']
})
export class DeleteCategoryDialogComponent extends DeleteDialog<DeleteCategoryDialogComponent> {

  constructor(
    public dialogRef: MatDialogRef<DeleteCategoryDialogComponent>,
    public service: CategoryService,
    @Inject(MAT_DIALOG_DATA) public data: Category
  ) {
    super(dialogRef, service, data);
  }
}
