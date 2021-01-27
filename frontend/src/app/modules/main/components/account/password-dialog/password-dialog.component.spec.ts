import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
import { UserService } from 'src/app/services/user/user.service';

import { PasswordDialogComponent } from './password-dialog.component';

describe('PasswordDialogComponent', () => {
  let component: PasswordDialogComponent;
  let fixture: ComponentFixture<PasswordDialogComponent>;
  let fb: FormBuilder;
  let dialogRef: MatDialogRef<PasswordDialogComponent>;
  let authService: AuthService;
  let userService: UserService;
  let messageService: MessageService;

  beforeEach(() => {
    let authServiceMock = {
      changePassword: jasmine.createSpy('changePassword').and.returnValue(of())
    };

    let userServiceMock = {
      update: jasmine.createSpy('update').and.returnValue(of()),
    };

    let messageServiceMock = {
      openSnackBar: jasmine.createSpy('openSnackBar').and.returnValue(of(true)),
    };

    let snackBarMock = {};

    TestBed.configureTestingModule({
      declarations: [PasswordDialogComponent],
      providers: [
        FormBuilder,
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: AuthService, useValue: authServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: MatSnackBar, useValue: snackBarMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(PasswordDialogComponent);
    component = fixture.componentInstance;
    //fixture.detectChanges();

    fb = TestBed.inject(FormBuilder);
    authService = TestBed.inject(AuthService);
    userService = TestBed.inject(UserService);
    messageService = TestBed.inject(MessageService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  class ChangePasswordData {
    oldPassword: string;
    newPassword: string;
    confirmPassword: string;
  }

  function setFormValues(formData: ChangePasswordData) {
    component.resetForm.patchValue({ ...formData });
  }

  it('should submit with valid form', () => {
    const validData = {
      oldPassword: '12345',
      newPassword: '54321',
      confirmPassword: '54321',
    };
    setFormValues(validData);
    component.onSubmit();
    expect(component.resetForm.valid).toBeTruthy();
    expect(authService.changePassword).toHaveBeenCalledWith(
      validData.oldPassword,
      validData.newPassword
    );
  });

  it('should not submit with too short old password', () => {
    const data = {
      oldPassword: '12',
      newPassword: '54321',
      confirmPassword: '54321',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });

  it('should not submit with empty old password', () => {
    const data = {
      oldPassword: '',
      newPassword: '54321',
      confirmPassword: '54321',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });

  it('should not submit with empty new password', () => {
    const data = {
      oldPassword: '12345',
      newPassword: '',
      confirmPassword: '54321',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });

  it('should not submit with empty confirm password', () => {
    const data = {
      oldPassword: '12345',
      newPassword: '54321',
      confirmPassword: '',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });

  it('should not submit with unmatching passwords - new password', () => {
    const data = {
      oldPassword: '12345',
      newPassword: '543',
      confirmPassword: '54321',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });

  it('should not submit with unmatching passwords - confirm password', () => {
    const data = {
      oldPassword: '12345',
      newPassword: '54321',
      confirmPassword: '543',
    };
    setFormValues(data);
    component.onSubmit();
    expect(component.resetForm.valid).toBeFalsy();
    expect(authService.changePassword).toHaveBeenCalledTimes(0);
  });
});
