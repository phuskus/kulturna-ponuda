import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import Page from 'src/app/shared/models/Page';

import { Review } from 'src/app/shared/models/Review';

import { ReviewService } from './review.service';

describe('ReviewService', () => {
  let injector;
  let reviewService: ReviewService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ReviewService]
    });

    injector = getTestBed();
    reviewService = TestBed.inject(ReviewService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(reviewService).toBeTruthy();
  });

  it('getForOfferId() should return a page of reviews for that offer', fakeAsync(() => {
    let response: Page<Review>;
    const someDate: Date = new Date();
    const offerId = 1;
    const pageNumber = 0;
    const pageSize = 10;
    const mockResponse: Page<Review> = {
      content: [
        {
          content: 'some review 1',
          culturalOfferId: offerId,
          culturalOfferName: 'CulturalOffer1',
          datePosted: someDate,
          id: 1,
          pictures: [],
          rating: 3,
          user: {
            id: 12,
            name: 'user12',
            password: 'some pass hash',
            surname: 'surname12',
            username: 'username12'
          }
        },
        {
          content: 'some review 1',
          culturalOfferId: 1,
          culturalOfferName: 'CulturalOffer1',
          datePosted: someDate,
          id: 1,
          pictures: [],
          rating: 3,
          user: {
            id: 12,
            name: 'user12',
            password: 'some pass hash',
            surname: 'surname12',
            username: 'username12'
          }
        },
        {
          content: 'some review 1',
          culturalOfferId: 1,
          culturalOfferName: 'CulturalOffer1',
          datePosted: someDate,
          id: 1,
          pictures: [],
          rating: 3,
          user: {
            id: 12,
            name: 'user12',
            password: 'some pass hash',
            surname: 'surname12',
            username: 'username12'
          }
        }
      ],
      empty: false,
      first: true,
      last: false,
      number: 0,
      numberofElements: 3,
      pageable: {
        offset: 0,
        pageNumber: pageNumber,
        pageSize: pageSize,
        paged: true,
        sort: {
          sorted: true,
          unsorted: false,
          empty: false
        },
        unpaged: false
      },
      size: pageSize,
      sort: {
        sorted: true,
        unsorted: false,
        empty: false
      },
      totalElements: 3,
      totalPages: 1
    };

    
    reviewService.getForOfferId(offerId, pageNumber, pageSize).subscribe(res => response = res);

    const request = httpMock.expectOne(`${reviewService.url}/offer/${offerId}?pageNo=${pageNumber}&pageSize=${pageSize}`);
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response).toEqual(mockResponse);
  }));

  it('addMultipart() should upload the selected image along with the review', fakeAsync(() => {
    let response: Review;
    const someDate: Date = new Date();
    const mockResponse: Review = {
      content: 'asdasd',
      culturalOfferId: 1,
      culturalOfferName: 'Museum_0',
      datePosted: someDate,
      id: 12091,
      pictures: [
        {
          id: 14,
          image: '',
          path: 'images/0202019.png',
          placeholder: 'someplaceholder.png'
        }
      ],
      rating: 3,
      user: {
        id: 11,
        name: 'Kayla',
        password: '$2a$10$vCNIcq18.NVKaHOm9fzoT.nT4rXHRdwyp.xU.ccl199Qe6cq46iEu',
        surname: 'Ernser',
        username: 'kaitlyn.jast@yahoo.com'
      }
    };

    const requestReview: Review = {"id":-1,"rating":3,"content":"asdasd","datePosted":someDate,"user":{"id":11,"name":"Kayla","surname":"Ernser","username":"kaitlyn.jast@yahoo.com","password":""},"culturalOfferId":1,"culturalOfferName":"Museum_0","pictures":[]}

    const requestFileList: FileList = {
      length: 1,
      item(index: number) { 
        let file: File;
        return file;
       }
    }

    reviewService.addMultipart(requestReview, requestFileList)
      .subscribe(res => response = res);

    const request = httpMock.expectOne(reviewService.url);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.content).toEqual(mockResponse.content);
    expect(response.culturalOfferId).toEqual(mockResponse.culturalOfferId);
    expect(response.culturalOfferName).toEqual(mockResponse.culturalOfferName);
    expect(response.datePosted).toEqual(mockResponse.datePosted);
    expect(response.id).toEqual(mockResponse.id);
    expect(response.pictures).toBeDefined();
    expect(response.pictures[0]).toEqual(mockResponse.pictures[0]);
    expect(response.rating).toEqual(mockResponse.rating);
    expect(response.user).toBeDefined();
    expect(response.user).toEqual(mockResponse.user);
    
  }));
});
