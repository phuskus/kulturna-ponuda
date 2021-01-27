import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Category } from 'src/app/shared/models/Category';

import { CategoryCardComponent } from './category-card.component';

describe('CategoryCardComponent', () => {
  let component: CategoryCardComponent;
  let fixture: ComponentFixture<CategoryCardComponent>;
  let pathService: PathExtractionService;

  beforeEach(() => {
    let pathServiceMock = {
      getFullImgPath: jasmine
        .createSpy('getFullImgPath')
        .and.returnValue(of('https://i.imgur.com/MVwP2rI.jpg')),
    };

    TestBed.configureTestingModule({
      declarations: [CategoryCardComponent],
      providers: [
        { provide: PathExtractionService, useValue: pathServiceMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(CategoryCardComponent);
    component = fixture.componentInstance;
    pathService = TestBed.inject(PathExtractionService);
    let inputCategory: Category = {
      id: 1,
      name: 'Test',
      subcategories: [
        {
          id: 1,
          name: 'Subcat 1',
          categoryId: 1,
          categoryName: 'Test',
          icon: {
            id: 1,
            placeholder: 'Placeholder',
            path: 'https://i.imgur.com/MVwP2rI.jpg',
            image: '',
          },
          containsOffers: true,
        },
        {
          id: 2,
          name: 'Subcat 2',
          categoryId: 1,
          categoryName: 'Test',
          icon: {
            id: 2,
            placeholder: 'Placeholder',
            path: 'https://i.imgur.com/MVwP2rI.jpg',
            image: '',
          },
          containsOffers: false,
        },
      ],
    };
    component.category = inputCategory;

    //fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have input info', () => {
    expect(component.category).toBeTruthy();
  });

  it('should execute init and filter empty subcategory', () => {
    expect(component.category.subcategories.length).toBe(2);
    expect(component.containsOffers).toBeTrue();
    component.ngOnInit();

    fixture.whenStable().then(() => {
      expect(component.subcategories.length).toBe(1);
      expect(component.containsOffers).toBeTrue();
    });
  });
});
