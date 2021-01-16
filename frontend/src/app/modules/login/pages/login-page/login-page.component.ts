import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';

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

  constructor(private readonly fb: FormBuilder, private router: Router, public authService: AuthService) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]]
    })
  }

  ngOnInit(): void {
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.errMsg = '';
    this.usernameErr = false;
    this.passwordErr = false;
    this.authService.login(this.loginForm.value['username'], this.loginForm.value['password']).subscribe(
      (loggedIn: boolean) => {
        if (loggedIn === true) {
          this.router.navigate(['/']);
        }
      }, error => {
        for (let key in error.errors) {
          this[key + "Err"] = true;
          this.errMsg = error.errors[key];
        }
      });
  }

  onChangeUsername() {
    if(this.usernameErr) {
      if(this.f.username.touched){
        this.usernameErr = false;
      }
    }
  }

  onChangePassword() {
    if(this.passwordErr) {
      if(this.f.password.touched){
        this.passwordErr = false;
      }
    }
  }

}
