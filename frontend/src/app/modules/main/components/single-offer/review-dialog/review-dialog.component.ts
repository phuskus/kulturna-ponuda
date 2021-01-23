import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { ReviewService } from 'src/app/services/review/review.service';
import Model from 'src/app/shared/models/Model';
import { Review } from 'src/app/shared/models/Review';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent
  extends AddDialog<ReviewDialogComponent>
  implements OnInit {
  public username: string = 'sstefann';
  private filesSelected: FileList;
  newObj: Review;

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    public service: ReviewService,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOffer
  ) {
    super(dialogRef, service);
  }
  ngOnInit(): void {
    this.newObj.culturalOfferId = this.data.id;
    this.newObj.culturalOfferName = this.data.name;
  }

  onRatingChanged(newValue: number): void {
    this.newObj.rating = newValue;
  }

  onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onSubmit(): void {
    console.log(this.newObj);
    console.log(this.filesSelected);
    this.service
      .addMultipart(this.newObj, this.filesSelected)
      .subscribe((data) => {
        this.onSubscriptionCallBack.emit(data);
      });
    this.dialogRef.close();
  }
}
