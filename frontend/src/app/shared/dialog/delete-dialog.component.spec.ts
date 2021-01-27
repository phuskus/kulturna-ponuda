import { Inject } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BaseService } from 'src/app/services/base/base.service';
import Model from '../models/Model';
import { async, fakeAsync, tick } from '@angular/core/testing';
import DeleteDialog from './DeleteDialog';

class MockModel extends Model {
  name: string;
}

class MockDialog extends DeleteDialog<MockModel> {}

describe('DeleteDialog', () => {
  let component: MockDialog;
  let fixture: ComponentFixture<MockDialog>;
  let service: BaseService;
  let dialog: MatDialogRef<MockModel>;
  let dialogData: MockModel;

  beforeEach(async () => {
    let serviceMock = {
      delete: jasmine.createSpy('delete').and.returnValue(of({})),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    TestBed.configureTestingModule({
      declarations: [MockDialog],
      providers: [
        { provide: BaseService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: { id: 1, name: 'Peter' } },
      ],
    });

    fixture = TestBed.createComponent(MockDialog);
    component = fixture.componentInstance;
    service = TestBed.inject(BaseService);
    dialog = TestBed.inject(MatDialogRef);
    dialogData = TestBed.inject(MAT_DIALOG_DATA);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dialog submit, return data and close', async(() => {
    spyOn(component.onSubscriptionCallBack, 'emit');
    component.onSubmit();

    expect(service.delete).toHaveBeenCalledTimes(1);
    expect(component.onSubscriptionCallBack.emit).toHaveBeenCalledTimes(1);
    expect(dialog.close).toHaveBeenCalledTimes(1);
  }));
});
