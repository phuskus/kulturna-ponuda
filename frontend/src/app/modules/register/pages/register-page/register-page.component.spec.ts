import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { FormBuilder } from '@angular/forms';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPageComponent } from './register-page.component';
import { of } from 'rxjs';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


describe('RegisterPageComponent', () => {
  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;
  let fb: FormBuilder;
  let authService: AuthService;
  let validationService: FormValidationService;
  let messageService: MessageService;

  beforeEach(() => {
    let authServiceMock = {
      register: jasmine.createSpy('register').and.returnValue(of()),
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
      declarations: [RegisterPageComponent],
      providers: [
        FormBuilder,
        { provide: AuthService, useValue: authServiceMock },
        { provide: FormValidationService, useValue: validationServiceMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: MatSnackBar, useValue: snackBarMock}
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterPageComponent);
    component = fixture.componentInstance;

    fb = TestBed.inject(FormBuilder);
    authService = TestBed.inject(AuthService);
    validationService = TestBed.inject(FormValidationService);
    messageService = TestBed.inject(MessageService);

    //fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  class RegisterFormData {
    name: string;
    surname: string;
    username: string;
    password: string;
    confirmPassword: string;
  }

  function setFormValues(formData: RegisterFormData) {
    component.registerForm.patchValue({ ...formData });
  }
  
  it('should submit with valid form', () => {
    const validData: RegisterFormData = {
      name: 'Name',
      surname: 'Surname',
      username: 'username@example.com',
      password: '12345',
      confirmPassword: '12345'
    };
    setFormValues(validData);

    component.onSubmit();

    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(0);

    expect(component.registerForm.valid).toBeTruthy();

    expect(authService.register).toHaveBeenCalledWith(
      validData.name,
      validData.surname,
      validData.username,
      validData.password
    );
  });

  it('should not submit with invalid username', () => {
    const validData: RegisterFormData = {
      name: 'Name',
      surname: 'Surname',
      username: 'username',
      password: '12345',
      confirmPassword: '12345'
    };
    setFormValues(validData);

    component.onSubmit();

    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(1);

    expect(component.registerForm.valid).toBeFalsy();

    expect(authService.register).toHaveBeenCalledTimes(0);
  });

  it('should not submit with invalid password', () => {
    const validData: RegisterFormData = {
      name: 'Name',
      surname: 'Surname',
      username: 'username@example.com',
      password: '1',
      confirmPassword: '1'
    };
    setFormValues(validData);

    component.onSubmit();

    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(1);

    expect(component.registerForm.valid).toBeFalsy();

    expect(authService.register).toHaveBeenCalledTimes(0);
  });
  
  it('should not submit with invalid duplicate password', () => {
    const validData: RegisterFormData = {
      name: 'Name',
      surname: 'Surname',
      username: 'username@example.com',
      password: '1',
      confirmPassword: '123'
    };
    setFormValues(validData);

    component.onSubmit();

    expect(validationService.validateAllFormFields).toHaveBeenCalledTimes(1);

    expect(component.registerForm.valid).toBeFalsy();

    expect(authService.register).toHaveBeenCalledTimes(0);
  });

});
