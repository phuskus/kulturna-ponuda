import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
})
export class SearchComponent implements OnInit {
  searchQuery: string = '';
  constructor() {}

  ngOnInit(): void {}

  onSubmit() {
    alert(`form submitted with query ${this.searchQuery}`);
    this.searchQuery = '';
  }
}
