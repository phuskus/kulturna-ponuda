import { Component, ViewChild, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  
  @ViewChild('form') loginForm: NgForm;
  hide : boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.loginForm);
  }

}
