import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReviewService } from 'src/app/services/review/review.service';
import Model from 'src/app/shared/models/Model';
import DeleteDialog from '../../../dialog/DeleteDialog';

@Component({
  selector: 'app-delete-review-dialog',
  templateUrl: './delete-review-dialog.component.html',
  styleUrls: ['./delete-review-dialog.component.scss'],
})
export class DeleteReviewDialogComponent extends DeleteDialog<DeleteReviewDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteReviewDialogComponent>,
    public service: ReviewService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service, data);
  }
}
