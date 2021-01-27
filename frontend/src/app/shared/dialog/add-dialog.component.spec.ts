import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BaseService } from 'src/app/services/base/base.service';
import { async, fakeAsync, tick } from '@angular/core/testing';
import Model from '../models/Model';
import AddDialog from './AddDialog';

class MockModel extends Model {
  name: string;
}

class MockDialog extends AddDialog<MockModel> {}

describe('AddDialog', () => {
  let component: MockDialog;
  let fixture: ComponentFixture<MockDialog>;
  let service: BaseService;
  let dialog: MatDialogRef<MockModel>;

  let mockData: MockModel;

  beforeEach(async () => {
    mockData = { id: 1, name: 'Peter' };
    let serviceMock = {
      add: jasmine.createSpy('add').and.returnValue(of(mockData)),
      createEmpty: jasmine
        .createSpy('createEmpty')
        .and.returnValue({ id: null, name: '' }),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    TestBed.configureTestingModule({
      declarations: [MockDialog],
      // imports: [MatSortModule, MatPaginatorModule],
      providers: [
        { provide: BaseService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
      ],
    });

    fixture = TestBed.createComponent(MockDialog);
    component = fixture.componentInstance;
    service = TestBed.inject(BaseService);
    dialog = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should dialog submit, return data and close', async(() => {
    spyOn(component.onSubscriptionCallBack, 'emit');
    component.onSubmit();

    expect(service.add).toHaveBeenCalledTimes(1);
    expect(component.onSubscriptionCallBack.emit).toHaveBeenCalledTimes(1);
    expect(dialog.close).toHaveBeenCalledTimes(1);
  }));
});
