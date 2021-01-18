import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';
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
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service);
    this.newObj = JSON.parse(JSON.stringify(data));
  }

  onSubmit() {
    this.service.update(this.newObj.id, this.newObj);
    this.dialogRef.close();
  }
}
