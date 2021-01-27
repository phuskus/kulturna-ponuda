import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { ReviewService } from 'src/app/services/review/review.service';
import Model from 'src/app/shared/models/Model';
import { Review } from 'src/app/shared/models/Review';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.scss'],
})
export class ReviewDialogComponent
  extends AddDialog<ReviewDialogComponent>
  implements OnInit {
  private filesSelected: FileList;
  newObj: Review;

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    public service: ReviewService,
    public userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOffer
  ) {
    super(dialogRef, service);
  }
  ngOnInit(): void {
    this.newObj.culturalOfferId = this.data.id;
    this.newObj.culturalOfferName = this.data.name;
    this.userService.getLoggedUser().subscribe((res) => {
      this.newObj.user = res;
    });
  }

  onRatingChanged(newValue: number): void {
    this.newObj.rating = newValue;
  }

  onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onSubmit(): void {
    this.service
      .addMultipart(this.newObj, this.filesSelected)
      .subscribe((data) => {
        this.onSubscriptionCallBack.emit(data);
      });
    this.dialogRef.close();
  }
}
