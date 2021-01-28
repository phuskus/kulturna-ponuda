import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';

@Component({
  selector: 'app-delete-offer-dialog',
  templateUrl: './delete-offer-dialog.component.html',
  styleUrls: ['./delete-offer-dialog.component.scss'],
})
export class DeleteOfferDialogComponent extends DeleteDialog<DeleteOfferDialogComponent> {
  constructor(
    public dialogRef: MatDialogRef<DeleteOfferDialogComponent>,
    public service: OfferService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOffer
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
