import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit {
  isLoggedIn : boolean;
  constructor(private router: Router, public authService: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = (localStorage['currentUser'] !== undefined);
  }

  logout() {
    this.isLoggedIn = false;
    this.authService.logout();
    this.router.navigateByUrl('/', { skipLocationChange: true });
    alert('Logged out! Current user: ' + localStorage['currentUser']);
  }

}
