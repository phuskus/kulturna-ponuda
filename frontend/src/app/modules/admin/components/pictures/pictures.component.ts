import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { MessageService } from 'src/app/services/message/message.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import Dialog from 'src/app/shared/dialog/Dialog';
import Model from 'src/app/shared/models/Model';
import { Picture } from 'src/app/shared/models/Picture';

@Component({
  selector: 'app-pictures',
  templateUrl: './pictures.component.html',
  styleUrls: ['./pictures.component.scss'],
})
export class PicturesComponent<T> extends Dialog<T> {
  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: OfferService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service, snackbar, messageService);
  }

  onSubmit() {
    console.log('submit');
  }

  getFullImgPath(picture: Picture): string {
    return AppSettings.API_ENDPOINT + picture.path;
  }
}
