import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { AdminService } from 'src/app/services/admin/admin.service';
import { Admin } from 'src/app/shared/models/Admin';

import { DeleteAdminDialogComponent } from './delete-admin-dialog.component';

describe('DeleteAdminDialogComponent', () => {
  let component: DeleteAdminDialogComponent;
  let fixture: ComponentFixture<DeleteAdminDialogComponent>;
  let service: AdminService;
  let dialog: MatDialogRef<Admin>;
  let dialogData: Admin;

  beforeEach(async () => {
    let serviceMock = {
      delete: jasmine.createSpy('delete').and.returnValue(of({})),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      declarations: [DeleteAdminDialogComponent],
      providers: [
        { provide: AdminService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: { id: 1, name: 'Peter' } },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteAdminDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(AdminService);
    dialog = TestBed.inject(MatDialogRef);
    dialogData = TestBed.inject(MAT_DIALOG_DATA);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
