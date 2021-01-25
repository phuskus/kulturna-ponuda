import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';
import Model from 'src/app/shared/models/Model';
import Dialog from './Dialog';

@Component({
  template: '',
})
export default abstract class DeleteDialog<T> extends Dialog<T> {
  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: BaseService,
    @Inject(MAT_DIALOG_DATA) public data: Model
  ) {
    super(dialogRef, service);
    if (!data) throw new Error('Data is a requied parameter for delete dialog');
  }

  onSubmit() {
    this.service.delete(this.data.id).subscribe((data) => {
      this.onSubscriptionCallBack.emit(data);
      this.dialogRef.close();
    });
    // this.dialogRef.close();
  }
}
