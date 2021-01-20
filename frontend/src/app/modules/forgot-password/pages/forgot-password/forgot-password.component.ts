import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: FormGroup;
  errMsg: String = '';
  usernameErr: boolean = false;

  constructor(private readonly fb: FormBuilder, 
    private router: Router, 
    private authService: AuthService, 
    private formValidationService: FormValidationService,
    private messageService : MessageService,
    private snackBar: MatSnackBar
    ) {
      this.forgotPasswordForm = this.fb.group({
        username: ['', [Validators.required, Validators.email]]
      })
     }

  ngOnInit(): void {
  }

  get f() {
    return this.forgotPasswordForm.controls;
  }

  onSubmit() {
    if (this.forgotPasswordForm.invalid || !this.forgotPasswordForm.value.username) {
      this.formValidationService.validateAllFormFields(this.forgotPasswordForm);
      return;
    }
    this.errMsg = '';
    this.usernameErr = false;
    this.authService.forgotPassword(
      this.forgotPasswordForm.value['username']
    ).subscribe( () => {
      this.router.navigate(['/login'])
      this.messageService.openSnackBar(this.snackBar, 'We sent an email with a link to get back into your account.', 'End', 7000);
    }, error => {
      console.log(error);
      if (error.status == 401) {
        //account not activated
        this.messageService.openSnackBar(this.snackBar, error.error.message, 'End', 5000);
        this.formValidationService.clearFormAndValidators(this.forgotPasswordForm);
      } else {
        //username not exists
        this.usernameErr = true;
        this.errMsg = error.error.message;
      }
    })
  }

  onTouchField(field: string, fieldErr: boolean) {
    this[field+'Err'] = this.formValidationService.onTouchField(this.f, field, fieldErr);
  }

}
