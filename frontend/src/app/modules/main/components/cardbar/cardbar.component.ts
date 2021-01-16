import { Category, CategoryService } from './../../../../services/category/category.service';
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cardbar',
  templateUrl: './cardbar.component.html',
  styleUrls: ['./cardbar.component.scss'],
})
export class CardbarComponent implements OnInit {
  public categories: Category[];
  
  constructor(private categoryService : CategoryService) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories() {
    return this.categoryService.getCategories().subscribe((res: Category[]) => {
      this.categories = res;
    })
  }
}
