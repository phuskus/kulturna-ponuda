import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';

@Component({
  selector: 'app-add-subcategory-dialog',
  templateUrl: './add-subcategory-dialog.component.html',
  styleUrls: ['./add-subcategory-dialog.component.scss'],
})
export class AddSubcategoryDialogComponent extends AddDialog<AddSubcategoryDialogComponent> {
  newObj: Subcategory;
  constructor(
    public dialogRef: MatDialogRef<AddSubcategoryDialogComponent>,
    public service: SubcategoryService
  ) {
    super(dialogRef, service);
  }

}
