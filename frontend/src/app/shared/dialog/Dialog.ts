import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BaseService } from 'src/app/services/base/base.service';
import Model from '../models/Model';

@Component({
  template: '',
})
export default abstract class Dialog<T> {
  @Output() onSubscriptionCallBack = new EventEmitter<Model>();

  constructor(public dialogRef: MatDialogRef<T>, public service: BaseService) {}

  abstract onSubmit(): void;
}
