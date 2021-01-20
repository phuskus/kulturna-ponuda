import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { MessageService } from 'src/app/services/message/message.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {
  isLoggedIn : boolean;
  constructor(
    private router: Router, 
    private authService: AuthService,
    private messageService: MessageService,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.isLoggedIn = (localStorage['currentUser'] !== undefined);
  }

  logout() {
    this.isLoggedIn = false;
    this.authService.logout();
    this.router.navigateByUrl('/', { skipLocationChange: true });
    this.messageService.openSnackBar(this.snackBar, 'Successfully logged out!', 'End', 5000);
  }

}
