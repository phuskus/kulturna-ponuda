import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Review } from 'src/app/shared/models/Review';


import { SingleReviewComponent } from './single-review.component';

describe('SingleReviewComponent', () => {
  let component: SingleReviewComponent;
  let fixture: ComponentFixture<SingleReviewComponent>;
  let pathService: PathExtractionService;

  beforeEach(() => {
    let pathServiceMock = {
      getFullImgPath: jasmine
        .createSpy('getFullImgPath')
        .and.returnValue(of('https://i.imgur.com/MVwP2rI.jpg')),
    };

    TestBed.configureTestingModule({
      declarations: [SingleReviewComponent],
      providers: [
        { provide: PathExtractionService, useValue: pathServiceMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(SingleReviewComponent);
    component = fixture.componentInstance;
    pathService = TestBed.inject(PathExtractionService);
    let review: Review = {
      id: 1,
      rating: 4,
      content: 'Test Content',
      culturalOfferId: 1,
      user: {
        id: 1,
        username: 'Test User',
        password: '',
        name: 'User',
        surname: 'Useric'
      },
      culturalOfferName: 'Test Offer',
      datePosted: new Date(),
      pictures: [
        {
          id: 1,
          placeholder: 'Placeholder',
          path: 'https://i.imgur.com/MVwP2rI.jpg',
          image: '',
        },
        {
          id: 2,
          placeholder: 'Placeholder2',
          path: 'https://i.imgur.com/MVwP2rI.jpg',
          image: '',
        },
        {
          id: 3,
          placeholder: 'Placeholder3',
          path: 'https://i.imgur.com/MVwP2rI.jpg',
          image: '',
        },
      ],
    };
    component.review = review;

    //fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should have input info', () => {
    expect(component.review).toBeTruthy();
  });

  it('should execute init and call full path', () => {
    expect(component.review.pictures.length).toBe(3);
    component.ngOnInit();

    fixture.whenStable().then(() => {
      expect(pathService.getFullImgPath).toHaveBeenCalledTimes(3);
    });
  });
});
