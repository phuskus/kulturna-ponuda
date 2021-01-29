import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BaseService } from 'src/app/services/base/base.service';
import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';
import Model from '../models/Model';

@Component({
  template: '',
})
export default abstract class Dialog<T> {
  // event to tell parent that dialog has finished its purpose
  // and return Observable data
  @Output() onSubscriptionCallBack = new EventEmitter<Model>();

  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: BaseService,
    public snackbar: MatSnackBar,
    public messageService: MessageService
  ) {}

  abstract onSubmit(): void;

  snackbarSuccess(message: string): void {
    this.showSnacbar(message, SnackbarColors.SUCCESS);
  }

  snackbarError(message: string): void {
    this.showSnacbar(message, SnackbarColors.ERROR);
  }

  private showSnacbar(message: string, color: SnackbarColors): void {
    this.messageService.openSnackBar(
      this.snackbar,
      message,
      'Close',
      2500,
      color
    );
  }
}
