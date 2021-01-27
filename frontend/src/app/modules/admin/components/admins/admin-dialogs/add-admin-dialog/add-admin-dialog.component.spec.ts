import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';
import { AdminService } from 'src/app/services/admin/admin.service';
import { Admin } from 'src/app/shared/models/Admin';

import { AddAdminDialogComponent } from './add-admin-dialog.component';

describe('AddAdminDialogComponent', () => {
  let component: AddAdminDialogComponent;
  let fixture: ComponentFixture<AddAdminDialogComponent>;
  let service: AdminService;
  let dialog: MatDialogRef<Admin>;

  beforeEach(async () => {
    let serviceMock = {
      add: jasmine.createSpy('add').and.returnValue(of({})),
      createEmpty: jasmine.createSpy('createEmpty').and.returnValue({
        id: 0,
        name: '',
        surname: '',
        username: '',
        password: '',
      }),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      declarations: [AddAdminDialogComponent],
      providers: [
        { provide: AdminService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdminDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(AdminService);
    dialog = TestBed.inject(MatDialogRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
