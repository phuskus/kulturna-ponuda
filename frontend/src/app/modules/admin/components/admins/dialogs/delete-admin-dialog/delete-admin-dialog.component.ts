import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import Model from 'src/app/shared/models/Model';
import DeleteDialog from '../../../dialog/DeleteDialog';

@Component({
  selector: 'app-delete-admin-dialog',
  templateUrl: './delete-admin-dialog.component.html',
  styleUrls: ['./delete-admin-dialog.component.scss'],
})
export class DeleteAdminDialogComponent extends DeleteDialog<DeleteAdminDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteAdminDialogComponent>,
    public service: AdminService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service, data);
  }
}
