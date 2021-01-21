import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user/user.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { User } from 'src/app/shared/models/User';

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.scss']
})
export class ProfileDialogComponent extends UpdateDialog<ProfileDialogComponent> {
  newObj: User;

  constructor(
    public dialogRef: MatDialogRef<ProfileDialogComponent>,
    public service: UserService,
    @Inject(MAT_DIALOG_DATA) public data: User
  ) {
    super(dialogRef, service, data);
   }

  ngOnInit(): void {
  }

}
