import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  profileForm: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<ProfileDialogComponent>,
    public service: UserService,
    @Inject(MAT_DIALOG_DATA) public data: User
  ) {
    super(dialogRef, service, data);
    this.profileForm = this.fb.group({
      username: [this.newObj.username],
      name: [this.newObj.name, [Validators.required]],
      surname: [this.newObj.surname, [Validators.required]]
    })
   }

  ngOnInit(): void {
  }

  get f() {
    return this.profileForm.controls;
  }

}
