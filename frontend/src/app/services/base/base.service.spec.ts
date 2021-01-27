import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import Model from 'src/app/shared/models/Model';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { BaseService } from './base.service';

class MockModel extends Model {
  name: String;
}

@Injectable({
  providedIn: 'root',
})
export class MockBaseService extends BaseService {
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

describe('BaseService', () => {
  let injector;
  let service: MockBaseService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MockBaseService],
    });

    injector = getTestBed();
    service = TestBed.inject(MockBaseService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('#getAllMockModel', () => {
    let expectedData: MockModel[];

    beforeEach(() => {
      expectedData = [
        { id: 0, name: 'Peter' },
        { id: 1, name: 'John' },
        { id: 2, name: 'Michael' },
        { id: 3, name: 'CHAD' },
      ];
    });

    it('should return expected models', fakeAsync(() => {
      service
        .getAll()
        .subscribe((data) => expect(data).toEqual(expectedData), fail);

      const req = httpMock.expectOne(service.url);
      expect(req.request.method).toBe('GET');
      req.flush(expectedData);
    }));

    it('should be OK returning no items', fakeAsync(() => {
      service
        .getAll()
        .subscribe((data) => expect(data.length).toEqual(0), fail);

      const req = httpMock.expectOne(service.url);
      expect(req.request.method).toBe('GET');
      req.flush([]);
    }));

    it('should turn 404 into error message', fakeAsync(() => {
      const msg = 'Deliberate 404';
      service.getAll().subscribe(
        (data) => fail('Expected to fail'),
        (err) => expect(err.message).toContain(msg)
      );

      const req = httpMock.expectOne(service.url);
      req.flush(msg, { status: 404, statusText: 'Not Found' });
    }));
  });

  describe('#getMockModelById', () => {
    const givenId = 1;
    let expectedData: MockModel;

    beforeEach(() => {
      expectedData = { id: givenId, name: 'Peter' };
    });

    it('should return mock model with given id', fakeAsync(() => {
      service.get(givenId).subscribe((data) => {
        expect(data).toEqual(expectedData);
        expect(givenId).toEqual(data.id);
      }, fail);

      const req = httpMock.expectOne(service.url + '/' + givenId);
      expect(req.request.method).toBe('GET');
      req.flush(expectedData);
    }));

    it('should turn 404 into nice error message', fakeAsync(() => {
      const msg = 'Deliberate 404';
      service.get(givenId).subscribe(
        (data) => fail('Expected to fail'),
        (err) => expect(err.message).toContain(msg)
      );

      const req = httpMock.expectOne(service.url + '/' + givenId);
      req.flush(msg, { status: 404, statusText: 'Not Found' });
    }));
  });

  describe('#addMockModel', () => {
    let expectedData: MockModel;

    beforeEach(() => {
      expectedData = { id: 1, name: 'Peter' };
    });

    it('should add new mock model and set its id', fakeAsync(() => {
      let actualData: MockModel = service.createEmpty();
      expect(actualData.id).toBeNull();

      service.add(actualData).subscribe((data) => {
        expect(data).toBeDefined();
        expect(data.id).toBeDefined();
        expect(data).toEqual(expectedData);
      }, fail);

      const req = httpMock.expectOne(service.url);
      expect(req.request.method).toBe('POST');
      req.flush(expectedData);
    }));
  });

  describe('#updateMockModel', () => {
    const givenId = 1;
    let oldData: MockModel;

    beforeEach(() => {
      oldData = { id: givenId, name: 'Peter' };
    });

    it('should update mock model and return it', fakeAsync(() => {
      let updatedData: MockModel = Object.assign({}, oldData);

      expect(oldData).toEqual(updatedData);

      updatedData.name = 'Not Peter';
      expect(oldData).not.toEqual(updatedData);

      service.update(givenId, updatedData).subscribe((data) => {
        expect(data).toBeDefined();
        expect(data).toEqual(updatedData);
      }, fail);

      const req = httpMock.expectOne(service.url + '/' + givenId);
      expect(req.request.method).toBe('PUT');
      req.flush(updatedData);
    }));
  });

  describe('#deleteMockModel', () => {
    beforeEach(() => {});

    it('should delete mock model', fakeAsync(() => {
      const givenId = 1;
      service.delete(givenId).subscribe((res) => {}, fail);

      const req = httpMock.expectOne(service.url + '/' + givenId);
      expect(req.request.method).toBe('DELETE');
      req.flush({});
    }));
  });
});
