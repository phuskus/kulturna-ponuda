import { HttpClient } from '@angular/common/http';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import Page from 'src/app/shared/models/Page';
import { Review } from 'src/app/shared/models/Review';

import { ReviewService } from './review.service';

describe('ReviewService', () => {
  let service: ReviewService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  let expectedReview: Review;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReviewService],
    });
    injector = getTestBed();
    service = TestBed.inject(ReviewService);
    service.url = 'api/reviews';
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);

    expectedReview = {
      id: null,
      rating: 3,
      content: 'Content',
      user: {
        id: 1,
        name: 'Peter',
        surname: 'Petric',
        username: 'user',
        password: '',
      },
      datePosted: new Date(),
      culturalOfferId: 3,
      culturalOfferName: 'Offer',
      pictures: [],
    };
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('#getReviewForOfferId', () => {
    let expectedReviews: Page<Review>;
    let pageNumber: number;
    let pageSize: number;
    let sortBy: string;
    let descending: boolean;

    beforeEach(() => {
      expectedReviews = {
        content: [expectedReview],
        totalElements: 1,
        totalPages: 1,
      };
      pageNumber = 0;
      pageSize = 3;
      sortBy = 'id';
      descending = false;
    });

    it('should return one page of objets', fakeAsync(() => {
      service
        .getPage(pageNumber, pageSize, sortBy, descending)
        .subscribe((data) => expect(data).toEqual(expectedReviews), fail);

      const req = httpMock.expectOne(
        `${service.url}?pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&descending=${descending}`
      );
      expect(req.request.method).toBe('GET');
      req.flush(expectedReviews);

      tick(2000);
    }));
  });

  describe('#addReview', () => {
    it('should add review with no images', fakeAsync(() => {
      let actualReview: Review = service.createEmpty();
      expect(actualReview).not.toEqual(expectedReview);

      service.addMultipart(actualReview, null).subscribe((data) => {
        expect(data).toBeDefined();
        expect(data.id).toBeDefined();
        expect(data).toEqual(expectedReview);
      }, fail);

      const req = httpMock.expectOne(service.url);
      expect(req.request.method).toBe('POST');
      req.flush(expectedReview);
    }));
  });
});
