import { TestBed } from '@angular/core/testing';
import { PathExtractionService } from './path-extraction.service';


describe('ImagePathExtractionService', () => {
  let service: PathExtractionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PathExtractionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
