import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';

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
  passwordErr: boolean = false;

  constructor(private readonly fb: FormBuilder, private router: Router, public authService: AuthService) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(5)]]
    })
  }

  ngOnInit(): void {
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    console.log('register submit');
  }

}
