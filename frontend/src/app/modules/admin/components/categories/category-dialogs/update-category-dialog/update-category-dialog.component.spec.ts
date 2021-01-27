import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { CategoryService } from 'src/app/services/category/category.service';
import { Category } from 'src/app/shared/models/Category';

import { UpdateCategoryDialogComponent } from './update-category-dialog.component';

describe('UpdateCategoryDialogComponent', () => {
  let component: UpdateCategoryDialogComponent;
  let fixture: ComponentFixture<UpdateCategoryDialogComponent>;
  let service: CategoryService;
  let dialog: MatDialogRef<Category>;
  let dialogData: Category;

  beforeEach(async () => {
    let serviceMock = {
      update: jasmine.createSpy('update').and.returnValue(of({})),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    await TestBed.configureTestingModule({
      declarations: [UpdateCategoryDialogComponent],
      providers: [
        { provide: CategoryService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: { id: 1, name: '' } },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCategoryDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CategoryService);
    dialog = TestBed.inject(MatDialogRef);
    dialogData = TestBed.inject(MAT_DIALOG_DATA);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
