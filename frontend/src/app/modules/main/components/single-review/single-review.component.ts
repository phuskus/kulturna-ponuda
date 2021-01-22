import { Component, Input, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-single-review',
  templateUrl: './single-review.component.html',
  styleUrls: ['./single-review.component.scss'],
})
export class SingleReviewComponent implements OnInit {
  @Input() public review: Review;

  constructor() {
    if (this.review) {
      console.log(this.review.pictures);
    }
  }
  ngOnInit(): void {
    this.review.pictures.forEach((picture) => {
      picture.path = AppSettings.API_ENDPOINT + picture.path;
    });
  }

  carouselCells() {
    return Math.min(3, this.review.pictures.length);
  }
}
