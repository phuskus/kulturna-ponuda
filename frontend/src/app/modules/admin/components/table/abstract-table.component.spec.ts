import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BaseService } from 'src/app/services/base/base.service';
import Dialog from 'src/app/shared/dialog/Dialog';
import Model from 'src/app/shared/models/Model';
import { AbstractTable } from './AbstractTable';
import { async, fakeAsync, tick } from '@angular/core/testing';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';

class MockComponentType extends Dialog<MockComponentType> {
  onSubmit(): void {
    throw new Error('Method not implemented.');
  }
}

class MockTable extends AbstractTable {}

describe('AbstractTable', () => {
  let component: MockTable;
  let fixture: ComponentFixture<MockTable>;
  let service: BaseService;
  let dialog: MatDialog;

  beforeEach(async () => {
    let serviceMock = {
      getAll: jasmine.createSpy('getAll').and.returnValue(
        of([
          { id: 0, name: 'Peter' },
          { id: 1, name: 'John' },
          { id: 2, name: 'Michael' },
          { id: 3, name: 'CHAD' },
        ])
      ),
      add: jasmine.createSpy('add'),
      update: jasmine.createSpy('update'),
      delete: jasmine.createSpy('delete'),
    };

    let dialogMock = {
      open: jasmine.createSpy('open'),
    };

    TestBed.configureTestingModule({
      declarations: [MockTable],
      imports: [MatSortModule, MatPaginatorModule],
      providers: [
        { provide: BaseService, useValue: serviceMock },
        { provide: MatDialog, useValue: dialogMock },
      ],
    });

    fixture = TestBed.createComponent(MockTable);
    component = fixture.componentInstance;
    service = TestBed.inject(BaseService);
    dialog = TestBed.inject(MatDialog);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch data on init', async(() => {
    component.ngAfterViewInit();

    expect(service.getAll).toHaveBeenCalled();

    // service mock has 4 elements
    fixture
      .whenStable()
      .then(() => expect(component.dataSource.data.length).toBe(4));
  }));

  it('should open dialog', () => {
    component.ngAfterViewInit();
    component.openDialog(MockComponentType);

    expect(dialog.open).toHaveBeenCalled();
  });

  it('should add to table', () => {
    const givenId = 1;
    let mockModel: Model = { id: givenId };
    component.add(mockModel);

    expect(service.add).toHaveBeenCalledWith(mockModel);
  });

  it('should update table', () => {
    const givenId = 1;
    let mockModel: Model = { id: givenId };
    component.update(givenId, mockModel);

    expect(service.update).toHaveBeenCalledWith(givenId, mockModel);
  });

  it('should delete from table', () => {
    const givenId = 1;
    component.delete(givenId);

    expect(service.delete).toHaveBeenCalledWith(givenId);
  });
});
