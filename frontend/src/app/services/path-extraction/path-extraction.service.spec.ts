import { TestBed } from '@angular/core/testing';
import { isObject } from 'rxjs/internal/util/isObject';
import { AppSettings } from 'src/app/app-settings/AppSettings';
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

  it('should add endpoint to string', () => {
    const testString = 'testString';
    const apiTestString = AppSettings.API_ENDPOINT + testString;

    const result = service.getFullImgPath(testString);
    expect(apiTestString).toEqual(result);
  })
});
