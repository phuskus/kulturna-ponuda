import { Injectable } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import Model from 'src/app/shared/models/Model';

import { BaseDynamicPagingService } from './base-dynamic-paging.service';
import { BaseService } from './base.service';

class MockModel extends Model {
  name: String;
}

@Injectable({
  providedIn: 'root',
})
class MockBaseDynamicPagingService extends BaseService {
  createEmpty(): MockModel {
    return {
      name: '',
      id: null,
    };
  }
}

describe('BaseDynamicPagingService', () => {
  let service: MockBaseDynamicPagingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MockBaseDynamicPagingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
