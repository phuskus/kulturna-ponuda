import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { Admin } from 'src/app/shared/models/Admin';

@Component({
  selector: 'app-delete-admin-dialog',
  templateUrl: './delete-admin-dialog.component.html',
  styleUrls: ['./delete-admin-dialog.component.scss'],
})
export class DeleteAdminDialogComponent extends DeleteDialog<DeleteAdminDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteAdminDialogComponent>,
    public service: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: Admin
  ) {
    super(dialogRef, service, data);
  }

  // TODO: Admin shouldn't be able to delete himself!!!!!!!!!!
}
