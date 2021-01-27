import { TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';

import { MessageService, SnackbarColors } from './message.service';

describe('MessageService', () => {
  let service: MessageService;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    const snackbarMock = {
      open: jasmine.createSpy('open'),
    };

    TestBed.configureTestingModule({
      providers: [{ provide: MatSnackBar, useValue: snackbarMock }],
    });

    service = TestBed.inject(MessageService);
    snackBar = TestBed.inject(MatSnackBar);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should open snackbar', () => {
    const message = 'Test message';
    const action = 'End';
    const duration = 1000;
    const color = SnackbarColors.SUCCESS;
    service.openSnackBar(snackBar, message, action, duration, color);
    expect(snackBar.open).toHaveBeenCalled();
  });

  it('should open snackbar with proper arguments', () => {
    const message = 'Test message';
    const action = 'End';
    const duration = 1000;
    const color = SnackbarColors.SUCCESS;
    service.openSnackBar(snackBar, message, action, duration, color);
    expect(snackBar.open).toHaveBeenCalledWith(message, action, {
      duration: duration,
      panelClass: ['success-snackbar'],
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  });
});
