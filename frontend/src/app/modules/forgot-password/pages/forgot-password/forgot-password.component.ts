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
    private activatedRoute: ActivatedRoute) {
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
    console.log('Forgot password');
  }

  onTouchField(field: string, fieldErr: boolean) {
    this[field+'Err'] = this.formValidationService.onTouchField(this.f, field, fieldErr);
  }

}
