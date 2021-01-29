import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProfileDialogComponent } from '../../components/account/profile-dialog/profile-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/shared/models/User';
import { PasswordDialogComponent } from '../../components/account/password-dialog/password-dialog.component';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { SubscriptionsTableComponent } from '../../components/subscriptions-table/subscriptions-table.component';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss'],
})
export class LandingPageComponent implements OnInit {
  isLoggedIn: boolean;
  isAdmin: boolean;
  constructor(
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private messageService: MessageService,
    private subscriptionService: SubscriptionService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.isLoggedIn = localStorage['currentUser'] !== undefined;
    this.isAdmin = this.authService.checkIfAdmin();
  }

  logout(): void {
    this.isLoggedIn = false;
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

  onEditProfile(): void {
    let currentUser = JSON.parse(localStorage['currentUser']);
    this.userService.get(currentUser.id).subscribe((res) => {
      this.dialog.open(ProfileDialogComponent, {
        autoFocus: false,
        data: res,
      });
    });
  }

  onEditSubscriptions(): void {
    this.subscriptionService.getPage(0, 10, 'id', true).subscribe(
      (subscriptions) => {
        this.dialog.open(SubscriptionsTableComponent, {
          autoFocus: false,
          data: subscriptions,
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }

  onChangePassword(): void {
    this.dialog.open(PasswordDialogComponent, {
      autoFocus: false,
      data: {},
    });
  }

  toAdminDashboard(): void {
    this.router.navigate(['/admin']);
  }
}
