import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminService } from 'src/app/services/admin/admin.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { Admin } from 'src/app/shared/models/Admin';
import { UserTokenState } from 'src/app/shared/models/UserTokenState';

@Component({
  selector: 'app-delete-admin-dialog',
  templateUrl: './delete-admin-dialog.component.html',
  styleUrls: ['./delete-admin-dialog.component.scss'],
})
export class DeleteAdminDialogComponent extends DeleteDialog<DeleteAdminDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteAdminDialogComponent>,
    public service: AdminService,
    public authService: AuthService,
    @Inject(MAT_DIALOG_DATA) public data: Admin
  ) {
    super(dialogRef, service, data);
  }

  onSubmit() {
    // check if he's trying to delete himself
    let user: UserTokenState = this.authService.getCurrentUser();
    if (user.id === this.data.id) {
      console.log('DONT DELETE YOURSELF MAAAN');
      this.dialogRef.close();
    } else {
      super.onSubmit();
    }
  }
}
