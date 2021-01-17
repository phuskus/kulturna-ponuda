import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';

@Component({
  template: '',
})
export default abstract class Dialog<T> {
  constructor(public dialogRef: MatDialogRef<T>, public service: BaseService) {}

  abstract onSubmit(): void;

  onAbort(): void {
    this.dialogRef.close();
  }
}
