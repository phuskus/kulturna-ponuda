import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  constructor() {}

  openSnackBar(
    snackBar: MatSnackBar,
    message: string,
    action: string,
    duration: number,
    color: SnackbarColors = SnackbarColors.INFO
  ) {
    let style: string;
    switch (color) {
      case SnackbarColors.SUCCESS:
        style = 'success-snackbar';
        break;
      case SnackbarColors.ERROR:
        style = 'error-snackbar';
        break;
      case SnackbarColors.INFO:
        style = 'info-snackbar';
        break;
    }
    snackBar.open(message, action, {
      duration: duration,
      panelClass: [style],
      verticalPosition: 'bottom',
      horizontalPosition: 'center',
    });
  }
}

export enum SnackbarColors {
  SUCCESS,
  ERROR,
  INFO,
}
