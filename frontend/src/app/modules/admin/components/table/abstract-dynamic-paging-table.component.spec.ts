import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { of } from 'rxjs';
import { BaseDynamicPagingService } from 'src/app/services/base/base-dynamic-paging.service';
import { AbstractDynamicPagingTable } from './AbstractDynamicPagingTable';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { async, fakeAsync, tick } from '@angular/core/testing';
import Page from 'src/app/shared/models/Page';
import Model from 'src/app/shared/models/Model';

class MockTable extends AbstractDynamicPagingTable {}
class MockModel extends Model {
  name: string;
}

describe('AbstractDynamicPagingTable', () => {
  let component: AbstractDynamicPagingTable;
  let fixture: ComponentFixture<AbstractDynamicPagingTable>;
  let service: BaseDynamicPagingService;
  let dialog: MatDialog;

  let sortMock;
  let paginatorMock;

  beforeEach(async () => {
    let returnVal: Page<MockModel> = {
      content: [
        { id: 0, name: 'Peter' },
        { id: 1, name: 'John' },
        { id: 2, name: 'Michael' },
        { id: 3, name: 'CHAD' },
      ],
      totalElements: 4,
      totalPages: 1,
    };
    let serviceMock = {
      getPage: jasmine.createSpy('getPage').and.returnValue(returnVal),
      search: jasmine.createSpy('search').and.returnValue(returnVal),
    };

    let dialogMock = {
      open: jasmine.createSpy('open'),
    };

    paginatorMock = {
      pageIndex: jasmine.createSpy('pageIndex'),
    };

    TestBed.configureTestingModule({
      declarations: [AbstractDynamicPagingTable, MatSort, MatPaginator],
      // imports: [MatSortModule, MatPaginatorModule],
      providers: [
        { provide: BaseDynamicPagingService, useValue: serviceMock },
        { provide: MatDialog, useValue: dialogMock },
      ],
    });

    fixture = TestBed.createComponent(AbstractDynamicPagingTable);
    component = fixture.componentInstance;
    service = TestBed.inject(BaseDynamicPagingService);
    dialog = TestBed.inject(MatDialog);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch data when no filter string', fakeAsync(() => {
    component.sort = new MatSort();
    component.paginator = paginatorMock;
    component.ngAfterViewInit();

    expect(service.getPage).toHaveBeenCalledTimes(1);
  }));

  it('should search data when it has filter string', fakeAsync(() => {
    component.sort = new MatSort();
    component.paginator = paginatorMock;
    // set filter text to something
    component.filter = 'query string';
    component.ngAfterViewInit();

    expect(service.search).toHaveBeenCalled();
  }));
});
