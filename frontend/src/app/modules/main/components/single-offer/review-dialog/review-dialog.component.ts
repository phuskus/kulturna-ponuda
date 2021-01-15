import { Component, Inject, OnInit } from '@angular/core';
import { ReviewDialogData } from 'src/app/shared/models/ReviewDialogData';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent {
  username: string = 'sstefann';

  comment: string;
  rating: number;
  pictures: Array<any>;

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ReviewDialogData
  ) {}

  addPictures() {
    alert('ojoj')
  }
}
