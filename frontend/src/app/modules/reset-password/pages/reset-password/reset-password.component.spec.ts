import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import { of } from 'rxjs';
import { ResetPasswordComponent } from './reset-password.component';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let fixture: ComponentFixture<ResetPasswordComponent>;
  let router: any;
  let authService: any;
  let formValidationService: any;
  let snackbar: any;
  let messageService: any;
  let activatedRoute: any;
  let fb: any;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetPasswordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    let routerMock = {
      navigate: jasmine.createSpy('navigate'),
      navigateByUrl: jasmine.createSpy('navigateByUrl')
    };

    let authServiceMock = {
      resetPassword: jasmine.createSpy('resetPassword').and.returnValue(of())
    };

    let snackBarMock = {

    };

    let formValidationServiceMock = {
      validateAllFormFields: jasmine
        .createSpy('validateAllFormFields')
        .and.returnValue(of(true)),
      onTouchField: jasmine.createSpy('onTouchField').and.returnValue(of()),
    };

    let activatedRouteMock = {
      snapshot: {
        params: {
          key: {}
        }
      }
    };

    let messageServiceMock = {
      openSnackBar: jasmine.createSpy('openSnackBar').and.returnValue(of(true)),
    };

    let formBuilderMock = {
      group: jasmine.createSpy('group').and.returnValue({ controls: {} })
    };

    TestBed.configureTestingModule({
      declarations: [ ResetPasswordComponent ],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: MatSnackBar, useValue: snackBarMock },
        { provide: FormValidationService, useValue: formValidationServiceMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        FormBuilder
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
    
    fixture = TestBed.createComponent(ResetPasswordComponent);
    component = fixture.componentInstance;

    fb = TestBed.inject(FormBuilder);
    router = TestBed.inject(Router);
    authService = TestBed.inject(AuthService);
    messageService = TestBed.inject(MessageService);
    snackBarMock = TestBed.inject(MatSnackBar);
    formValidationService = TestBed.inject(FormValidationService);
    activatedRoute = TestBed.inject(ActivatedRoute);

    fixture.detectChanges();
  });

  class ResetFormData {
    newPassword: string;
    confirmPassword: string;
  }

  function setFormValues(formData: ResetFormData) {
    component.resetForm.patchValue({ ...formData });
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call reset password', fakeAsync(() => {
    let formData: ResetFormData = {
      newPassword: 'asdasd123',
      confirmPassword: 'asdasd123'
    };

    setFormValues(formData);
    component.onSubmit();
    expect(authService.resetPassword).toHaveBeenCalled();
  }));
});
