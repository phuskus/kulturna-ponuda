import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { Subcategory } from 'src/app/shared/models/Subcategory';

import { SubcategoryService } from './subcategory.service';

describe('SubcategoryService', () => {
  let injector;
  let subcategoryService: SubcategoryService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubcategoryService]
    });

    injector = getTestBed();
    subcategoryService = TestBed.inject(SubcategoryService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(subcategoryService).toBeTruthy();
  });

  it('addMultipart() should upload the selected icon to be the icon of the Subcategory', fakeAsync(() => {
    let response: Subcategory;
    const mockResponse: Subcategory = {
      id: 12,
      name: 'Kobasicijada',
      categoryId: 2,
      categoryName: 'Manifestation',
      containsOffers: false,
      icon: {
        id: 13,
        image: "",
        path: 'images/828283.png',
        placeholder: 'kobasicijada.jpg'
      },
    };

    const requestSubcategory: Subcategory = {
      id: 0,
      name: 'Kobasicijada',
      categoryId: 2,
      categoryName: '',
      icon:
        {
          id: 0,
          placeholder: '',
          image: '',
          path: ''
        },
      containsOffers: false
    }

    const requestFileList: FileList = {
      length: 1,
      item(index: number) { 
        let file: File;
        return file;
       }
    }

    subcategoryService.addMultipart(requestSubcategory, requestFileList)
      .subscribe(res => response = res);

    const request = httpMock.expectOne(subcategoryService.url);
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.categoryId).toEqual(mockResponse.categoryId);
    expect(response.categoryName).toEqual(mockResponse.categoryName);
    expect(response.containsOffers).toEqual(mockResponse.containsOffers);
    expect(response.id).toEqual(mockResponse.id);
    expect(response.name).toEqual(response.name);
    expect(response.icon).toBeDefined();
    expect(response.icon.id).toEqual(mockResponse.icon.id);
    expect(response.icon.image).toEqual(mockResponse.icon.image);
    expect(response.icon.path).toEqual(mockResponse.icon.path);
    expect(response.icon.placeholder).toEqual(mockResponse.icon.placeholder);
  }));


});
