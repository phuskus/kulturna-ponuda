import { Component, Input, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { ImagePathExtractorComponent } from 'src/app/shared/components/image-path-extractor/image-path-extractor.component';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-single-review',
  templateUrl: './single-review.component.html',
  styleUrls: ['./single-review.component.scss'],
})
export class SingleReviewComponent
  extends ImagePathExtractorComponent
  implements OnInit {
  @Input() public review: Review;

  constructor() {
    super();
  }

  ngOnInit(): void {
    this.review.pictures.forEach((picture) => {
      picture.path = super.getFullImgPath(picture);
    });
  }

  carouselCells() {
    return Math.min(3, this.review.pictures.length);
  }
}
