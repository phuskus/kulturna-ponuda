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
class MockBaseService extends BaseService {
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

  it('rand', fakeAsync(() => {
    expect(service).toBeTruthy();
  }));

  it('should return expected models', fakeAsync(() => {
    const expectedData: MockModel[] = [
      { id: 0, name: '' },
      { id: 1, name: '' },
      { id: 2, name: '' },
      { id: 3, name: '' },
    ];

    let actualData: MockModel[] = [];

    service.getAll().subscribe((data) => (actualData = data as MockModel[]));

    const req = httpMock.expectOne(service.url);
    expect(req.request.method).toBe('GET');
    req.flush(expectedData);

    tick();

    expect(actualData).toEqual(expectedData, 'expected data');
  }));

  it('should return model with given id', fakeAsync(() => {
    const givenId = 1;
    const expectedData: MockModel = { id: givenId, name: 'Peter' };

    let actualData: MockModel = service.createEmpty();

    service.get(givenId).subscribe((data) => (actualData = data as MockModel));

    const req = httpMock.expectOne(service.url + '/' + givenId);
    expect(req.request.method).toBe('GET');
    req.flush(expectedData);

    tick();

    expect(actualData).toEqual(expectedData);
    expect(givenId).toEqual(expectedData.id);
  }));

  it('should add new model and set its id', fakeAsync(() => {
    const expectedData: MockModel = { id: 1, name: 'Peter' };

    let actualData: MockModel = service.createEmpty();
    expect(actualData.id).toBeNull();

    service
      .add(actualData)
      .subscribe((data) => (actualData = data as MockModel));

    const req = httpMock.expectOne(service.url);
    expect(req.request.method).toBe('POST');
    req.flush(expectedData);

    tick();

    expect(actualData).toBeDefined();
    expect(actualData.id).toBeDefined();
    expect(actualData).toEqual(expectedData);
  }));

  it('should update old data with new attributes', fakeAsync(() => {
    const givenId = 1;
    let oldData: MockModel = { id: givenId, name: 'Peter' };
    let updatedData: MockModel = service.createEmpty();
    Object.assign(updatedData, oldData);

    expect(oldData).toEqual(updatedData);

    updatedData.name = 'Not Pera';
    expect(oldData).not.toEqual(updatedData);

    service
      .update(updatedData.id, updatedData)
      .subscribe((data) => (oldData = data as MockModel));

    const req = httpMock.expectOne(service.url + '/' + givenId);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedData);

    tick();

    expect(oldData).toBeDefined();
    expect(oldData).toEqual(updatedData);
  }));

  it('should delete data', fakeAsync(() => {
    const givenId = 1;
    service.delete(givenId).subscribe((res) => {});

    const req = httpMock.expectOne(service.url + '/' + givenId);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  }));
});
