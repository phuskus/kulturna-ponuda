import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OfferService } from 'src/app/services/offer/offer.service';
import Dialog from 'src/app/shared/dialog/Dialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';

@Component({
  selector: 'app-images-dialog',
  templateUrl: './images-dialog.component.html',
  styleUrls: ['./images-dialog.component.scss']
})
export class ImagesDialogComponent<T> extends Dialog<T> {

  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: OfferService,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOffer
  ) {
    super(dialogRef, service);
   }

  onSubmit() {
    console.log('submit');
  }

}
