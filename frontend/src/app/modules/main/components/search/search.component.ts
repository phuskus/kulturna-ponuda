import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  @Input()
  searchQuery: string = '';
  constructor(private router: Router) { }
  
  ngOnInit(): void { }

  onSubmit() {
    const navigationExtras: NavigationExtras = {
      queryParams: { query: this.searchQuery },
      queryParamsHandling: 'merge'
    };
    this.router.navigate(['/offers/search'], navigationExtras);
  }
}
