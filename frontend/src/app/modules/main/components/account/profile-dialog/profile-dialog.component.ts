import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { UserService } from 'src/app/services/user/user.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { User } from 'src/app/shared/models/User';

@Component({
  selector: 'app-profile-dialog',
  templateUrl: './profile-dialog.component.html',
  styleUrls: ['./profile-dialog.component.scss'],
})
export class ProfileDialogComponent extends UpdateDialog<ProfileDialogComponent> {
  newObj: User;
  profileForm: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<ProfileDialogComponent>,
    public service: UserService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: User
  ) {
    super(dialogRef, service, snackbar, messageService, data);
    this.profileForm = this.fb.group({
      username: [this.newObj.username],
      name: [this.newObj.name, [Validators.required]],
      surname: [this.newObj.surname, [Validators.required]],
    });
  }

  ngOnInit(): void {}

  get f() {
    return this.profileForm.controls;
  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      this.newObj.name = this.profileForm.value['name'];
      this.newObj.surname = this.profileForm.value['surname'];
      this.service.update(this.newObj.id, this.newObj).subscribe(() => {
        this.snackbarSuccess('Successfully updated profile!')
        this.dialogRef.close();
      }, err => this.snackbarError("Failed to update profile!"));
    }
  }
}
