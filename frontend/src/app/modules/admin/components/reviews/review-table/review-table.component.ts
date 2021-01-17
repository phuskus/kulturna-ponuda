import { HttpClient } from '@angular/common/http';
import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, Observable, of as observableOf } from 'rxjs';
import { catchError, map, startWith, switchMap } from 'rxjs/operators';
import { ReviewService } from 'src/app/services/review/review.service';
import { Review } from 'src/app/shared/models/Review';
import { AbstractPagingTable } from '../../table/AbstractPagingTable';

@Component({
  selector: 'app-review-table',
  templateUrl: './review-table.component.html',
  styleUrls: ['./review-table.component.scss'],
})
export class ReviewTableComponent extends AbstractPagingTable<Review> {
  constructor(service: ReviewService) {
    super(service);
    this.displayedColumns = ['id', 'offerId', 'user', 'rating', 'content'];
  }
}
