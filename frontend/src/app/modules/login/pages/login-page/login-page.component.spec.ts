import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { LoginPageComponent } from './login-page.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('LoginPageComponent', () => {
  let component: LoginPageComponent;
  let fixture: ComponentFixture<LoginPageComponent>;
  let fb: FormBuilder;
  let authService: AuthService;
  let validationService: FormValidationService;
  let messageService: MessageService;

  beforeEach(() => {
    let authServiceMock = {
      login: jasmine.createSpy('login').and.returnValue(of()),
    };

    let validationServiceMock = {
      validateAllFormFields: jasmine
        .createSpy('validateAllFormFields')
        .and.returnValue(of(true)),
      onTouchField: jasmine.createSpy('onTouchField').and.returnValue(of()),
    };

    let messageServiceMock = {
      openSnackBar: jasmine.createSpy('openSnackBar').and.returnValue(of(true)),
    };

    let snackBarMock = {
      
    };

    TestBed.configureTestingModule({
      declarations: [ LoginPageComponent ],
      imports: [ RouterTestingModule ],
      providers: [
        FormBuilder,
        { provide: AuthService, useValue: authServiceMock },
        { provide: FormValidationService, useValue: validationServiceMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: MatSnackBar, useValue: snackBarMock}
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginPageComponent);
    component = fixture.componentInstance;

    fb = TestBed.inject(FormBuilder);
    authService = TestBed.inject(AuthService);
    validationService = TestBed.inject(FormValidationService);
    messageService = TestBed.inject(MessageService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  class LoginFormData {
    username: string;
    password: string;
  }

  function setFormValues(formData: LoginFormData) {
    component.loginForm.patchValue({ ...formData});
  }

  it('should submit with valid form', () => {
    const validData: LoginFormData = {
      username: 'yahoo@yahoo.com',
      password: '12345'
    };

    setFormValues(validData);
    component.onSubmit();

    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);
    expect(component.loginForm.valid).toBeTruthy();

    expect(authService.login).toHaveBeenCalledWith(
      validData.username,
      validData.password
    );
  });

  it('should not submit with empty username', () => {
    const data: LoginFormData = {
      username: '',
      password: '12345'
    };
    setFormValues(data);
    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);
    expect(component.loginForm.valid).toBeFalsy();
    expect(authService.login).toHaveBeenCalledTimes(0);
  });

  it('should not submit with invalid username', () => {
    const data : LoginFormData = {
      username: 'yahoo',
      password: '12345'
    };
    setFormValues(data);
    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);
    expect(component.loginForm.valid).toBeFalsy();
    expect(authService.login).toHaveBeenCalledTimes(0);
  });

  it('should not submit with empty password', () => {
    const data: LoginFormData = {
      username: 'yahoo@yahoo.com',
      password: ''
    };
    setFormValues(data);
    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);
    expect(component.loginForm.valid).toBeFalsy();
    expect(authService.login).toHaveBeenCalledTimes(0);
  });

  it('should not submit with too short password', () => {
    const data: LoginFormData = {
      username: 'yahoo@yahoo.com',
      password: '123'
    };
    setFormValues(data);
    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);
    expect(component.loginForm.valid).toBeFalsy();
    expect(authService.login).toHaveBeenCalledTimes(0);
  })
  
});
