import { HttpClient } from '@angular/common/http';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { Injectable } from '@angular/core';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import Model from 'src/app/shared/models/Model';
import Page from 'src/app/shared/models/Page';

import { BaseDynamicPagingService } from './base-dynamic-paging.service';
import { BaseService } from './base.service';

class MockModel extends Model {
  name: String;
}

@Injectable({
  providedIn: 'root',
})
class MockBaseDynamicPagingService extends BaseDynamicPagingService {
  constructor(public http: HttpClient) {
    super(http);
    this.url = 'api/model';
  }

  createEmpty(): MockModel {
    return {
      name: '',
      id: null,
    };
  }
}

describe('BaseDynamicPagingService', () => {
  let service: MockBaseDynamicPagingService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MockBaseDynamicPagingService],
    });
    injector = getTestBed();
    service = TestBed.inject(MockBaseDynamicPagingService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return a page model object', fakeAsync(() => {
    const expectedData: Page<MockModel> = {
      content: [
        { id: 1, name: 'Peter' },
        { id: 2, name: 'Steven' },
        { id: 3, name: 'John' },
      ],
      totalPages: 1,
      totalElements: 3,
    };

    let actualData: Page<MockModel> = {
      content: [],
      totalElements: 0,
      totalPages: 0,
    };

    const pageNumber = 0;
    const pageSize = 3;
    const sortBy = 'id';
    const descending = false;
    service
      .getPage(pageNumber, pageSize, sortBy, descending)
      .subscribe((data) => (actualData = data as Page<MockModel>));

    const req = httpMock.expectOne(
      `${service.url}?pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&descending=${descending}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(expectedData);

    tick(2000);

    console.log(actualData);
    expect(actualData).toEqual(expectedData, 'expected data');
  }));

  it('should return a page model object', fakeAsync(() => {
    const expectedData: Page<MockModel> = {
      content: [
        { id: 1, name: 'Peter' },
        { id: 2, name: 'Prima Ballerina' },
        { id: 3, name: 'Python' },
      ],
      totalPages: 1,
      totalElements: 3,
    };

    let actualData: Page<MockModel> = {
      content: [],
      totalElements: 0,
      totalPages: 0,
    };

    const query = 'P';
    const pageNumber = 0;
    const pageSize = 3;
    const sortBy = 'id';
    const descending = false;
    service
      .search(query, pageNumber, pageSize, sortBy, descending)
      .subscribe((data) => (actualData = data as Page<MockModel>));

    const req = httpMock.expectOne(
      `${service.url}/search?query=${query}&pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&descending=${descending}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(expectedData);

    tick(2000);

    console.log(actualData);
    expect(actualData).toEqual(expectedData, 'expected data');
  }));
});
