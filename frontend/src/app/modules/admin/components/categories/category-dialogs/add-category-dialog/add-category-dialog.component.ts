import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CategoryService } from 'src/app/services/category/category.service';
import { MessageService } from 'src/app/services/message/message.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { Category } from 'src/app/shared/models/Category';

@Component({
  selector: 'app-add-category-dialog',
  templateUrl: './add-category-dialog.component.html',
  styleUrls: ['./add-category-dialog.component.scss'],
})
export class AddCategoryDialogComponent extends AddDialog<AddCategoryDialogComponent> {
  newObj: Category;

  constructor(
    public dialogRef: MatDialogRef<AddCategoryDialogComponent>,
    public service: CategoryService,
    public snackbar: MatSnackBar,
    public messageService: MessageService
  ) {
    super(dialogRef, service, snackbar, messageService);
  }
}
