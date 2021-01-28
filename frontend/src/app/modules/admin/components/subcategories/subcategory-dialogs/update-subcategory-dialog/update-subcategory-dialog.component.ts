import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';

@Component({
  selector: 'app-update-subcategory-dialog',
  templateUrl: './update-subcategory-dialog.component.html',
  styleUrls: ['./update-subcategory-dialog.component.scss'],
})
export class UpdateSubcategoryDialogComponent extends UpdateDialog<UpdateSubcategoryDialogComponent> {
  newObj: Subcategory;
  cultForm: FormGroup;
  constructor(
    public dialogRef: MatDialogRef<UpdateSubcategoryDialogComponent>,
    public service: SubcategoryService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Subcategory
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
