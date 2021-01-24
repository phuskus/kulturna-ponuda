import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';

@Component({
  selector: 'app-update-subcategory-dialog',
  templateUrl: './update-subcategory-dialog.component.html',
  styleUrls: ['./update-subcategory-dialog.component.scss'],
})
export class UpdateSubcategoryDialogComponent extends UpdateDialog<UpdateSubcategoryDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<UpdateSubcategoryDialogComponent>,
    public service: SubcategoryService,
    @Inject(MAT_DIALOG_DATA) public data: Subcategory
  ) {
    super(dialogRef, service, data);
  }
}
