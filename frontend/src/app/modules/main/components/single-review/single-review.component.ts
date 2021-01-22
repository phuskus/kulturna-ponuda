import { Component, Input, OnInit } from '@angular/core';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-single-review',
  templateUrl: './single-review.component.html',
  styleUrls: ['./single-review.component.scss'],
})
export class SingleReviewComponent {
  @Input() public review: Review;

  constructor() {}

  carouselCells() {
    return Math.min(3, this.review.pictures.length);
  }
}
