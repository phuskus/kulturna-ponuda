import { Subcategory } from 'src/app/shared/models/Subcategory';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';

@Component({
  selector: 'app-delete-subcategory-dialog',
  templateUrl: './delete-subcategory-dialog.component.html',
  styleUrls: ['./delete-subcategory-dialog.component.scss'],
})
export class DeleteSubcategoryDialogComponent extends DeleteDialog<DeleteSubcategoryDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteSubcategoryDialogComponent>,
    public service: SubcategoryService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Subcategory
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
