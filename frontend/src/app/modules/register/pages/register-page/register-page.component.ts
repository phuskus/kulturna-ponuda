import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  registerForm: FormGroup;
  hide: boolean = true;
  errMsg: String = '';
  usernameErr: boolean = false;

  constructor(private readonly fb: FormBuilder, 
    private router: Router, 
    private authService: AuthService, 
    private formValidationService: FormValidationService,
    private snackbar: MatSnackBar) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]],
      confirmPassword: ['', Validators.required]
    })
  }

  ngOnInit(): void {
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.formValidationService.validateAllFormFields(this.registerForm);
      return;
    }
    this.errMsg = '';
    this.usernameErr = false;
    this.authService.register(
      this.registerForm.value['name'],
      this.registerForm.value['surname'],
      this.registerForm.value['username'],
      this.registerForm.value['password']
    ).subscribe(
      () => {
        this.openSnackbar();
        this.formValidationService.clearFormAndValidators(this.registerForm);
      }, error => {
        for (let key in error.errors) {
          this[key + "Err"] = true;
          this.errMsg = error.errors[key];
        }
    });
  }

  onTouchField(field: string, fieldErr: boolean) {
    this.usernameErr = this.formValidationService.onTouchField(this.f, field, fieldErr);
  }

  openSnackbar() {
    this.snackbar.open('Successfully registered! Please check your email and activate your account!', 'End', {
      duration: 5000,
      verticalPosition: 'top', 
      horizontalPosition: 'center'
    });
  }

}
