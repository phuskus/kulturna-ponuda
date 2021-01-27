import { Component, Input, OnInit } from '@angular/core';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-single-review',
  templateUrl: './single-review.component.html',
  styleUrls: ['./single-review.component.scss'],
})
export class SingleReviewComponent implements OnInit {
  @Input() public review: Review;

  constructor(public pathService: PathExtractionService) {}

  ngOnInit(): void {
    this.review.pictures.forEach((picture) => {
      picture.path = this.pathService.getFullImgPath(picture.path);
    });
  }

  carouselCells(): number {
    return Math.min(3, this.review.pictures.length);
  }
}
