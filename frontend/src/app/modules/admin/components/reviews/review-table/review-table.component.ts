import { Component } from '@angular/core';
import { ReviewService } from 'src/app/services/review/review.service';
import { Review } from 'src/app/shared/models/Review';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';

@Component({
  selector: 'app-review-table',
  templateUrl: './review-table.component.html',
  styleUrls: ['./review-table.component.scss'],
})
export class ReviewTableComponent extends AbstractDynamicPagingTable<Review> {
  constructor(service: ReviewService) {
    super(service);
    this.tableColumns = ['id', 'offerId', 'user', 'rating', 'content'];
  }
}
