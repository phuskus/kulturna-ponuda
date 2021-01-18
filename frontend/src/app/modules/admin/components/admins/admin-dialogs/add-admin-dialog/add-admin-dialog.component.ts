import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import { Admin } from 'src/app/shared/models/Admin';
import Model from 'src/app/shared/models/Model';
import AddDialog from '../../../dialog/AddDialog';

@Component({
  selector: 'app-add-admin-dialog',
  templateUrl: './add-admin-dialog.component.html',
  styleUrls: ['./add-admin-dialog.component.scss'],
})
export class AddAdminDialogComponent extends AddDialog<AddAdminDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<AddAdminDialogComponent>,
    public service: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: Admin
  ) {
    super(dialogRef, service, data);
  }
}
