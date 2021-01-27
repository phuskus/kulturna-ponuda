import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef } from '@angular/material/dialog';
import { of } from 'rxjs';
import { CategoryService } from 'src/app/services/category/category.service';
import { Category } from 'src/app/shared/models/Category';

import { AddCategoryDialogComponent } from './add-category-dialog.component';

describe('AddCategoryDialogComponent', () => {
  let component: AddCategoryDialogComponent;
  let fixture: ComponentFixture<AddCategoryDialogComponent>;
  let service: CategoryService;
  let dialog: MatDialogRef<Category>;

  beforeEach(async () => {
    let serviceMock = {
      add: jasmine.createSpy('add').and.returnValue(of({})),
      createEmpty: jasmine
        .createSpy('createEmpty')
        .and.returnValue({ id: 0, name: '' }),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      declarations: [AddCategoryDialogComponent],
      providers: [
        { provide: CategoryService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCategoryDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CategoryService);
    dialog = TestBed.inject(MatDialogRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
