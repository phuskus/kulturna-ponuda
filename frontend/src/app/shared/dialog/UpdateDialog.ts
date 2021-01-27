import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BaseService } from 'src/app/services/base/base.service';
import { MessageService } from 'src/app/services/message/message.service';
import Model from 'src/app/shared/models/Model';
import Dialog from './Dialog';

@Component({
  template: '',
})
export default abstract class UpdateDialog<T> extends Dialog<T> {
  newObj: Model;

  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: BaseService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service, snackbar, messageService);
    if (!data) throw new Error('Data is a requied parameter for update dialog');

    this.newObj = JSON.parse(JSON.stringify(data));
  }

  onSubmit(): void {
    this.service.update(this.newObj.id, this.newObj).subscribe(
      (data) => {
        this.onSubscriptionCallBack.emit(data);
        this.snackbarSuccess('Successfully updated!');
      },
      (err: Error) => this.snackbarError(err.message)
    );
    this.dialogRef.close();
  }
}
