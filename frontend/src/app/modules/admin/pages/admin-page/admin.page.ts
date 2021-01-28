import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin.page.html',
  styleUrls: ['./admin.page.scss'],
})
export class AdminPage {
  public links = [{ icon: 'dashboard', text: 'Dashboard', route: '/admin' }];
  constructor(
    public authService: AuthService,
    public router: Router,
    public messageService: MessageService,
    public snackBar: MatSnackBar
  ) {}

  getLastUrlPath() {
    return '/' + window.location.pathname.split('/').pop();
  }

  openLink() {
    let win = window.open('https://youtu.be/dQw4w9WgXcQ?t=43', 'blank');
    win.focus();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/', { skipLocationChange: true });
    this.messageService.openSnackBar(
      this.snackBar,
      'Successfully logged out!',
      'End',
      5000,
      SnackbarColors.SUCCESS
    );
  }
}
