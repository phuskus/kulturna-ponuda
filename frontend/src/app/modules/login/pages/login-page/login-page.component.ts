import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  
  loginForm: FormGroup;
  hide : boolean = true;
  data: {};

  constructor(private readonly fb: FormBuilder, private router: Router, public authService: AuthService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
   }

  ngOnInit(): void {
  }

  onSubmit() {
    this.authService.login(this.loginForm.value['username'], this.loginForm.value['password']).subscribe((loggedIn:boolean) => {
      if(loggedIn) {
        this.router.navigate(['/']);
        //console.log(localStorage['currentUser']);
      }
      //todo validation
    });
  }

}
