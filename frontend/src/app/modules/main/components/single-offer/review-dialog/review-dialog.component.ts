import { Component, Inject } from '@angular/core';
// import { ReviewDialogData } from 'src/app/shared/models/ReviewDialogData';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent {
  public username: string = 'sstefann';
  public rating: number = 0;
  public comment: string;
  private filesSelected: FileList;

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ReviewDialogData
  ) {}

  onRatingChanged(newValue: number): void {
    this.rating = newValue;
  }

  onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onReviewSubmit(): void {
    alert('Your review has been submitted');
    this.dialogRef.close();
  }
}


interface ReviewDialogData {
  offerName: string;
}
