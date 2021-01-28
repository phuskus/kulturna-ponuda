import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { ReviewService } from 'src/app/services/review/review.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import Model from 'src/app/shared/models/Model';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-delete-review-dialog',
  templateUrl: './delete-review-dialog.component.html',
  styleUrls: ['./delete-review-dialog.component.scss'],
})
export class DeleteReviewDialogComponent extends DeleteDialog<DeleteReviewDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteReviewDialogComponent>,
    public service: ReviewService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Review
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
