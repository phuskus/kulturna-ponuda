import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category/category.service';
import { MessageService } from 'src/app/services/message/message.service';
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
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Category
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
