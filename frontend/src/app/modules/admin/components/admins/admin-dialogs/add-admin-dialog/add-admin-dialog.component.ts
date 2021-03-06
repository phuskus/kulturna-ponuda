import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { Admin } from 'src/app/shared/models/Admin';

@Component({
  selector: 'app-add-admin-dialog',
  templateUrl: './add-admin-dialog.component.html',
  styleUrls: ['./add-admin-dialog.component.scss'],
})
export class AddAdminDialogComponent extends AddDialog<AddAdminDialogComponent> {
  newObj: Admin;

  constructor(
    public dialogRef: MatDialogRef<AddAdminDialogComponent>,
    public service: AdminService
  ) {
    super(dialogRef, service);
  }

}
