import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService, SnackbarColors } from 'src/app/services/message/message.service';
import { UserService } from 'src/app/services/user/user.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';

@Component({
  selector: 'app-password-dialog',
  templateUrl: './password-dialog.component.html',
  styleUrls: ['./password-dialog.component.scss']
})
export class PasswordDialogComponent extends UpdateDialog<PasswordDialogComponent> {
  
  resetForm: FormGroup;
  hidePassword: boolean = true;
  hideNewPassword: boolean = true;
  hideConfirmPassword: boolean = true;
  
  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<PasswordDialogComponent>,
    public service: UserService,
    public authService: AuthService,
    public snack: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: any) { 
      super(dialogRef, service, data)
      this.resetForm = this.fb.group({
        oldPassword: ['', [Validators.required, Validators.minLength(5)]],
        newPassword: ['', [Validators.required, Validators.minLength(5)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(5)]]
      })
  }

  ngOnInit(): void {
  }

  get f() {
    return this.resetForm.controls;
  }

  onSubmit(): void {
    if(this.resetForm.valid) {
      this.authService.changePassword(
        this.resetForm.value['oldPassword'], 
        this.resetForm.value['newPassword']).subscribe( () => {
          this.messageService.openSnackBar(this.snack, 'Successfully changed password!', 'End', 5000, SnackbarColors.SUCCESS);
          this.dialogRef.close();
        }, error => {
          this.messageService.openSnackBar(this.snack, 'Please check your old password!', 'End', 5000, SnackbarColors.ERROR);
        })
    }
  }

}
