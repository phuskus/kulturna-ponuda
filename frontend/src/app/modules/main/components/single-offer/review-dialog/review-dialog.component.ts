import { Component, Inject } from '@angular/core';
import { ReviewDialogData } from 'src/app/shared/models/ReviewDialogData';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent {
  username: string = 'sstefann';
  rating: number = 0;
  comment: string;
  filesSelected: FileList;

  picturePaths: Array<string>;

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ReviewDialogData
  ) {}

  onRatingChanged(newValue: number): void {
    this.rating = newValue;
  }

  onFilesSelected(event: FileList): void {
    this.filesSelected = event;
  }

  onReviewSubmit(): void {
    alert('Your review has been submitted');
    this.dialogRef.close();
  }
}
