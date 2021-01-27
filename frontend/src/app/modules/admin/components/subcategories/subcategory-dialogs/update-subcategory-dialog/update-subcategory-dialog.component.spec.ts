import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSubcategoryDialogComponent } from './update-subcategory-dialog.component';

describe('UpdateSubcategoryDialogComponent', () => {
  let component: UpdateSubcategoryDialogComponent;
  let fixture: ComponentFixture<UpdateSubcategoryDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateSubcategoryDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateSubcategoryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
});
