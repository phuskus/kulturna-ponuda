import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin.page.html',
  styleUrls: ['./admin.page.scss'],
})
export class AdminPage {
  public links = [
    { icon: 'dashboard', text: 'Dashboard', route: '/admin' },
    { icon: 'person', text: 'Account', route: '/account' },
  ];
  constructor() {}

  getLastUrlPath() {
    return '/' + window.location.pathname.split('/').pop();
  }

  openLink() {
    let win = window.open('https://youtu.be/dQw4w9WgXcQ?t=43', 'blank'); 
    win.focus();
  }
}
