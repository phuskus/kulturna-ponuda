import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';
import Model from 'src/app/shared/models/Model';
import Dialog from './Dialog';
import DialogData from './DialogData';

@Component({
  template: '',
})
export default abstract class UpdateDialog<T> extends Dialog<T> {
  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: BaseService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service);
  }

  onSubmit() {
    this.service.update(this.data.id, this.data);
    this.dialogRef.close();
  }

  onAbort() {
    this.dialogRef.close();
  }
}
