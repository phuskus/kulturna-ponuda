import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category/category.service';
import { MessageService } from 'src/app/services/message/message.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { Category } from 'src/app/shared/models/Category';

@Component({
  selector: 'app-update-category-dialog',
  templateUrl: './update-category-dialog.component.html',
  styleUrls: ['./update-category-dialog.component.scss'],
})
export class UpdateCategoryDialogComponent extends UpdateDialog<UpdateCategoryDialogComponent> {
  newObj: Category;
  constructor(
    public dialogRef: MatDialogRef<UpdateCategoryDialogComponent>,
    public service: CategoryService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Category
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
