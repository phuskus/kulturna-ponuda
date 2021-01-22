import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormControl } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginForm: FormGroup;
  hide: boolean = true;
  errMsg: String = '';
  usernameErr: boolean = false;
  passwordErr: boolean = false;

  constructor(private readonly fb: FormBuilder, 
    private router: Router, 
    private authService: AuthService, 
    private formValidationService: FormValidationService,
    private activatedRoute: ActivatedRoute,
    private messageService : MessageService,
    private snackBar: MatSnackBar) {
    
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]]
    })
  }

  ngOnInit(): void {
    this.acccountActivation();
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    if (this.loginForm.invalid || !this.loginForm.value.username || !this.loginForm.value.password) {
      this.formValidationService.validateAllFormFields(this.loginForm);
      return;
    }
    this.errMsg = '';
    this.usernameErr = false;
    this.passwordErr = false;
    this.authService.login(
      this.loginForm.value['username'], 
      this.loginForm.value['password']).subscribe(
        (loggedIn: boolean) => {
          if (loggedIn === true) {
            this.router.navigate(['/']);
          }
        }, error => {
          if (error.status == 401) {
            //account not activated
            this.messageService.openSnackBar(this.snackBar, error.error.message, 'End', 5000);
            this.formValidationService.clearFormAndValidators(this.loginForm);
          }  else {
            //username not exists or incorrect password
            for (let key in error.error.errors) {
              this[key + "Err"] = true;
              this.errMsg = error.error.errors[key];
            }
          }
        });
  }

  onTouchField(field: string, fieldErr: boolean) {
    this[field+'Err'] = this.formValidationService.onTouchField(this.f, field, fieldErr);
  }

  acccountActivation() {
    let key = this.activatedRoute.snapshot.params.key;
    if(key) {
      this.authService.activateAccount(key).subscribe( () => {
        this.messageService.openSnackBar(this.snackBar, 'Successfully activated account!', 'End', 5000);
      }, error => {
        console.log(error.errors);
        this.messageService.openSnackBar(this.snackBar, error.errors, 'End', 5000);
      })
      
    }
  }

}
