import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { CategoryService } from 'src/app/services/category/category.service';
import { Category } from 'src/app/shared/models/Category';

import { CardbarComponent } from './cardbar.component';

describe('CardbarComponent', () => {
  let component: CardbarComponent;
  let fixture: ComponentFixture<CardbarComponent>;
  let categoryService: CategoryService;

  beforeEach(() => {
    const categories: Category[] = [
      {
        id: 1,
        name: 'Test',
        subcategories: []
      }
    ];
    let categoryServiceMock = {
      getAll: jasmine.createSpy('getAll').and.returnValue(of(categories)),
    };

    TestBed.configureTestingModule({
      declarations: [CardbarComponent],
      providers: [
        { provide: CategoryService, useValue: categoryServiceMock },
      ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
    }).compileComponents();


    fixture = TestBed.createComponent(CardbarComponent);
    component = fixture.componentInstance;
    categoryService = TestBed.inject(CategoryService);
    
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch on init', () => {
    component.ngOnInit();

    expect(categoryService.getAll).toHaveBeenCalled();
    fixture.whenStable()
    .then(() => {
      expect(component.categories.length).toBe(1);
      const category: Category = component.categories[0];
      expect(category.name).toBe('Test');
      expect(category.subcategories.length).toBe(0);
    })
  });
});
