import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { Admin } from 'src/app/shared/models/Admin';
import { AddAdminDialogComponent } from '../add-admin-dialog/add-admin-dialog.component';

@Component({
  selector: 'app-update-admin-dialog',
  templateUrl: './update-admin-dialog.component.html',
  styleUrls: ['./update-admin-dialog.component.scss']
})
export class UpdateAdminDialogComponent extends UpdateDialog<UpdateAdminDialogComponent> {

  newObj: Admin;

  constructor(
    public dialogRef: MatDialogRef<UpdateAdminDialogComponent>,
    public service: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: Admin
  ) {
    super(dialogRef, service, data);
  }


}
