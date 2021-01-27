import { Component, Input, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  @Input()
  public searchQuery: string = '';
  constructor(private router: Router) {}

  ngOnInit(): void {}

  onSubmit(): void {
    const navigationExtras: NavigationExtras = {
      queryParams: { query: this.searchQuery },
      queryParamsHandling: 'merge',
    };
    this.router.navigate(['/offers/search'], navigationExtras);
  }
}
