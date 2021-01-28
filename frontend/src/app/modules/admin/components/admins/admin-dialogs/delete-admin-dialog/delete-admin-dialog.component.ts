import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService } from 'src/app/services/admin/admin.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
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
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Admin
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }

  onSubmit() {
    // check if he's trying to delete himself
    let user: UserTokenState = this.authService.getCurrentUser();
    if (user.id === this.data.id) {
      this.snackbarError("You can't delete yourself!");
      this.dialogRef.close();
    } else {
      super.onSubmit();
    }
  }
}
