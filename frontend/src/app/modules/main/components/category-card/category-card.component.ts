import { Component, Input, OnInit } from '@angular/core';
import { Category } from 'src/app/shared/models/Category';
import { Subcategory } from 'src/app/shared/models/Subcategory';

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrls: ['./category-card.component.scss']
})
export class CategoryCardComponent implements OnInit {

  @Input() category: Category;

  public containsOffers: boolean = true;

  public subcategories: Subcategory[];

  constructor() { }

  ngOnInit(): void {
    if (this.category.subcategories.length === 0) {
      this.containsOffers = false;
    } else {
      let allSubcatsEmpty = true; 
      this.category.subcategories.forEach(subcat => {
        if (subcat.containsOffers) {
          allSubcatsEmpty = false;
        }
      });
      this.containsOffers = !allSubcatsEmpty;
    }
    this.subcategories = this.category.subcategories.filter(sc => sc.containsOffers)
  }

}
