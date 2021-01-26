import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';
import Model from 'src/app/shared/models/Model';
import Dialog from './Dialog';

@Component({
  template: '',
})
export default abstract class AddDialog<T> extends Dialog<T> {
  newObj: Model;
  constructor(public dialogRef: MatDialogRef<T>, public service: BaseService) {
    super(dialogRef, service);
    this.newObj = service.createEmpty();
  }

  onSubmit() {
    this.service.add(this.newObj).subscribe((data) => {
      this.onSubscriptionCallBack.emit(data);
      this.dialogRef.close();
    });
  }
}
