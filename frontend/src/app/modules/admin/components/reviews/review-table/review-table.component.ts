import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ReviewService } from 'src/app/services/review/review.service';
import { Review } from 'src/app/shared/models/Review';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';
import { DeleteReviewDialogComponent } from '../dialogs/delete-review-dialog/delete-review-dialog.component';

@Component({
  selector: 'app-review-table',
  templateUrl: './review-table.component.html',
  styleUrls: ['./review-table.component.scss'],
})
export class ReviewTableComponent extends AbstractDynamicPagingTable {
  constructor(service: ReviewService, public dialog: MatDialog) {
    super(service, dialog);
    this.tableColumns = ['offerId', 'user', 'rating', 'content', 'actions'];
  }

  openDeleteDialog(row: Review): void {
    this.openDialog(DeleteReviewDialogComponent, row);
  }
}
