import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BaseService } from 'src/app/services/base/base.service';
import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';
import Model from 'src/app/shared/models/Model';
import Dialog from './Dialog';

@Component({
  template: '',
})
export default abstract class AddDialog<T> extends Dialog<T> {
  newObj: Model;
  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: BaseService,
    public snackbar: MatSnackBar,
    public messageService: MessageService
  ) {
    super(dialogRef, service, snackbar, messageService);
    this.newObj = service.createEmpty();
  }

  onSubmit(): void {
    this.service.add(this.newObj).subscribe(
      (data) => {
        this.onSubscriptionCallBack.emit(data);
        this.snackbarSuccess(`Successfully Added!`);
      },
      (err: Error) => this.snackbarError(err.message)
    );
    this.dialogRef.close();
  }
}
