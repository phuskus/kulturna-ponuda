import { HttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  HttpClientTestingModule,
} from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import Page from 'src/app/shared/models/Page';
import { PageParams } from 'src/app/shared/models/PageParams';

import { OfferService } from './offer.service';

describe('OfferService', () => {
  let service: OfferService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OfferService],
    });
    service = TestBed.inject(OfferService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get 1 for search query', fakeAsync(() => {
    let offers: CulturalOffer[];

    const mockOffers: Page<CulturalOffer> = {
      content: [
        {
          id: 1,
          address: 'Test address 14',
          admin: 1,
          averageRating: 2,
          category: 1,
          categoryName: 'Test category',
          city: 'Novi Sad',
          description: 'Test description',
          latitude: 44.0,
          longitude: 44.0,
          name: 'Test offer',
          pictures: [],
          posts: [],
          region: 'Vojvodina',
          reviews: [],
        },
      ],
      totalPages: 1,
      totalElements: 1,
    };
    const pageParams: PageParams = {
      descending: false,
      pageNo: 0,
      pageSize: 5,
      sortBy: 'id',
    };

    const category = '';
    const query = 'test';
    const regionNames = '';
    const cityNames = '';

    service
      .searchFilterCulturalOffers(
        pageParams,
        category,
        query,
        regionNames,
        cityNames
      )
      .subscribe((data) => {
        offers = data.content;
        expect(data.totalPages).toBe(1);
        expect(data.totalElements).toBe(1);
      });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT +
        'cultural_offers/search?' +
        'pageNo=' +
        pageParams.pageNo.toString() +
        '&pageSize=' +
        pageParams.pageSize.toString() +
        '&sortBy=' +
        pageParams.sortBy +
        '&descending=' +
        pageParams.descending.toString() +
        '&categoryName=' +
        category +
        '&query=' +
        query +
        '&regionNames=' +
        regionNames +
        '&cityNames=' +
        cityNames
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockOffers);

    tick();

    expect(offers).toEqual(mockOffers.content);
  }));

  it('should get 2 for category name', fakeAsync(() => {
    let offers: CulturalOffer[];

    const mockOffers: Page<CulturalOffer> = {
      content: [
        {
          id: 1,
          address: 'Test address 14',
          admin: 1,
          averageRating: 2,
          category: 1,
          categoryName: 'Test category',
          city: 'Novi Sad',
          description: 'Test description',
          latitude: 44.0,
          longitude: 44.0,
          name: 'Test offer',
          pictures: [],
          posts: [],
          region: 'Vojvodina',
          reviews: [],
        },
        {
          id: 1,
          address: 'Test address 14',
          admin: 1,
          averageRating: 2,
          category: 1,
          categoryName: 'Test category',
          city: 'Novi Sad',
          description: 'Test description',
          latitude: 44.0,
          longitude: 44.0,
          name: 'Test offer',
          pictures: [],
          posts: [],
          region: 'Vojvodina',
          reviews: [],
        },
      ],
      totalPages: 1,
      totalElements: 2,
    };
    const pageParams: PageParams = {
      descending: false,
      pageNo: 0,
      pageSize: 5,
      sortBy: 'id',
    };

    const category = 'test';
    const query = '';
    const regionNames = '';
    const cityNames = '';

    service
      .searchFilterCulturalOffers(
        pageParams,
        category,
        query,
        regionNames,
        cityNames
      )
      .subscribe((data) => {
        offers = data.content;
        expect(data.totalPages).toBe(1);
        expect(data.totalElements).toBe(2);
      });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT +
        'cultural_offers/search?' +
        'pageNo=' +
        pageParams.pageNo.toString() +
        '&pageSize=' +
        pageParams.pageSize.toString() +
        '&sortBy=' +
        pageParams.sortBy +
        '&descending=' +
        pageParams.descending.toString() +
        '&categoryName=' +
        category +
        '&query=' +
        query +
        '&regionNames=' +
        regionNames +
        '&cityNames=' +
        cityNames
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockOffers);

    tick();

    expect(offers).toEqual(mockOffers.content);
  }));


  it('should add new offer with no images', fakeAsync(() => {
    let cultOffer: CulturalOffer = {
      id: -1,
      address: 'Test address 14',
      admin: 1,
      averageRating: 2,
      category: 1,
      categoryName: 'Test category',
      city: 'Novi Sad',
      description: 'Test description',
      latitude: 44.0,
      longitude: 44.0,
      name: 'Test offer',
      pictures: [],
      posts: [],
      region: 'Vojvodina',
      reviews: [],
    };
    const mockCultOffer: CulturalOffer = {
      id: 1,
      address: 'Test address 14',
      admin: 1,
      averageRating: 2,
      category: 1,
      categoryName: 'Test category',
      city: 'Novi Sad',
      description: 'Test description',
      latitude: 44.0,
      longitude: 44.0,
      name: 'Test offer',
      pictures: [],
      posts: [],
      region: 'Vojvodina',
      reviews: [],
    };

    service.addMultipart(cultOffer, null).subscribe((data) => {
      cultOffer = data;
    });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT + 'cultural_offers'
    );
    expect(req.request.method).toBe('POST');
    req.flush(mockCultOffer);

    tick();
    
    expect(cultOffer).toBe(mockCultOffer);
  }));

});
