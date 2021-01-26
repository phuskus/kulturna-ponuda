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

  let expectedData: Page<MockModel>;
  let pageNumber: number;
  let pageSize: number;
  let sortBy: string;
  let descending: boolean;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MockBaseDynamicPagingService],
    });
    injector = getTestBed();
    service = TestBed.inject(MockBaseDynamicPagingService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);

    expectedData = {
      content: [
        { id: 1, name: 'Peter' },
        { id: 2, name: 'Prima Ballerina' },
        { id: 3, name: 'Python' },
      ],
      totalPages: 1,
      totalElements: 3,
    };
    pageNumber = 0;
    pageSize = 3;
    sortBy = 'id';
    descending = false;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('#getPage', () => {
    it('should return one page of objets', fakeAsync(() => {
      service
        .getPage(pageNumber, pageSize, sortBy, descending)
        .subscribe((data) => expect(data).toEqual(expectedData), fail);

      const req = httpMock.expectOne(
        `${service.url}?pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&descending=${descending}`
      );
      expect(req.request.method).toBe('GET');
      req.flush(expectedData);

      tick(2000);
    }));
  });

  describe('#search', () => {
    let query: string;

    beforeEach(() => {
      query = 'P';
    });

    it('should search by query and return page of objects', fakeAsync(() => {
      const query = 'P';
      service
        .search(query, pageNumber, pageSize, sortBy, descending)
        .subscribe((data) => expect(data).toEqual(expectedData), fail);

      const req = httpMock.expectOne(
        `${service.url}/search?query=${query}&pageNo=${pageNumber}&pageSize=${pageSize}&sortBy=${sortBy}&descending=${descending}`
      );
      expect(req.request.method).toBe('GET');
      req.flush(expectedData);

      tick(2000);
    }));
  });
});
