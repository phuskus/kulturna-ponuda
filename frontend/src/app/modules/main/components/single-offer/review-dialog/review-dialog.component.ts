import { Component, Inject } from '@angular/core';
// import { ReviewDialogData } from 'src/app/shared/models/ReviewDialogData';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AddAdminDialogComponent } from 'src/app/modules/admin/components/admins/admin-dialogs/add-admin-dialog/add-admin-dialog.component';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { ReviewService } from 'src/app/services/review/review.service';
import Model from 'src/app/shared/models/Model';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent extends AddDialog<ReviewDialogComponent> {
  public username: string = 'sstefann';
  private filesSelected: FileList;

  public newReview: Review = {
    id: null,
    rating: 0,
    content: '',
    user: {
      id: 279,
      name: 'Celia',
      surname: 'James',
      username: 'Marian',
      password: '',
    },
    culturalOfferId: this.data.id,
    culturalOfferName: this.data.name,
    pictures: [],
  };

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    public service: ReviewService,
    @Inject(MAT_DIALOG_DATA) public data: ReviewDialogData // Should be Cult Offer
  ) {
    super(dialogRef, service);
  }

  onRatingChanged(newValue: number): void {
    this.newReview.rating = newValue;
  }

  onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onReviewSubmit(): void {
    alert('Your review has been submitted');
    this.service.add(this.newReview);
    this.dialogRef.close();
  }
}

interface ReviewDialogData extends Model {
  name: string;
}
