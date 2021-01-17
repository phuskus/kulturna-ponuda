import { TestBed } from '@angular/core/testing';

import { BaseDynamicPagingService } from './base-dynamic-paging.service';

describe('BaseDynamicPagingService', () => {
  let service: BaseDynamicPagingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BaseDynamicPagingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
