import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/shared/models/User';

import { ProfileDialogComponent } from './profile-dialog.component';

describe('ProfileDialogComponent', () => {
  let component: ProfileDialogComponent;
  let fixture: ComponentFixture<ProfileDialogComponent>;
  let fb: FormBuilder;
  let dialogRef: MatDialogRef<ProfileDialogComponent>;
  let userService: UserService;

  
  beforeEach(() => {
    let userServiceMock = {
      update: jasmine.createSpy('update').and.returnValue(of())
    }

    TestBed.configureTestingModule({
      declarations: [ProfileDialogComponent],
      providers: [
        FormBuilder,
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: UserService, useValue: userServiceMock }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(ProfileDialogComponent);
    component = fixture.componentInstance;
    //fixture.detectChanges();

    fb = TestBed.inject(FormBuilder);
    userService = TestBed.inject(UserService);

    const user: User = {
      id: 1,
      username: 'test@test.com',
      name: 'test',
      surname: 'test',
      password: ''
    };

    component.newObj = user;
  });

  class UserData {
    username: string;
    name: string;
    surname: string;
  }

  function setFormValues(formData: UserData) {
    component.profileForm.patchValue({...formData});
    component.newObj.name = formData.name;
    component.newObj.surname = formData.surname;
  }
  
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should submit with valid form', (done) => {
    const validData = {
      username: 'test@test.com',
      name: 'test1',
      surname: 'test1',
    };
    const user: User = {
      id: 1,
      username: 'test@test.com',
      name: 'test1',
      surname: 'test1',
      password: ''
    };
    setFormValues(validData);
    fixture.whenStable().then(() => {
      expect(component.profileForm.valid).toBeTruthy();
      component.onSubmit();
      fixture.whenStable().then(() => {
        expect(userService.update).toHaveBeenCalledWith(
          user.id,
          user
        );
        done();
      }) 
    })
  })
});
