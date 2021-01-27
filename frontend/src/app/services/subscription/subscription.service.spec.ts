import { getTestBed, TestBed } from '@angular/core/testing';
import { SubscriptionService } from './subscription.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, tick } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http'
import { IsSubscribedResponse } from 'src/app/shared/models/IsSubscribedResponse';
import { SubscriptionResponse } from 'src/app/shared/models/SubscriptionResponse';

describe('SubscriptionService', () => {
  let injector;
  let subscriptionService: SubscriptionService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubscriptionService]
    });

    injector = getTestBed();
    subscriptionService = TestBed.inject(SubscriptionService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(subscriptionService).toBeTruthy();
  });

  it('getIsSubscribedToOffer() should tell me if i am subscribed to an offer', fakeAsync(() => {
    let response: IsSubscribedResponse;
    const mockResponse: IsSubscribedResponse = {
      subscribed: true
    }
    
    subscriptionService.getIsSubscribedToOffer(1).subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/offer/1');
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.subscribed).toEqual(true);
  }));

  it('subscribeToOffer() should subscribe me to an offer', fakeAsync(() => {
    let response: SubscriptionResponse;
    const mockResponse: SubscriptionResponse = {
      message: 'You have been successfully subscribed to offer 1'
    }
    
    subscriptionService.subscribeToOffer(1).subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/subscribeOffer/1');
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.message).toEqual('You have been successfully subscribed to offer 1');
  }));

  it('unsubscribeFromOffer() should unsubscribe me from an offer', fakeAsync(() => {
    let response: SubscriptionResponse;
    const mockResponse: SubscriptionResponse = {
      message: 'You have been successfully unsubscribed from offer 1'
    }
    
    subscriptionService.unsubscribeFromOffer(1).subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/unsubscribeOffer/1');
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.message).toEqual('You have been successfully unsubscribed from offer 1');
  }));

  it('getIsSubscribedToSubcategory() should tell me if i am subscribed to a subcategory', fakeAsync(() => {
    let response: IsSubscribedResponse;
    const mockResponse: IsSubscribedResponse = {
      subscribed: true
    }
    
    subscriptionService.getIsSubscribedToSubcategory('SomeSubcategory').subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/subcategory/SomeSubcategory');
    expect(request.request.method).toBe('GET');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.subscribed).toEqual(true);
  }));

  it('subscribeToSubcategory() should subscribe me to a subcategory', fakeAsync(() => {
    let response: SubscriptionResponse;
    const mockResponse: SubscriptionResponse = {
      message: 'You have been successfully subscribed to subcategory SomeSubcategory'
    }
    
    subscriptionService.subscribeToSubcategory('SomeSubcategory').subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/subscribeSubcategory/SomeSubcategory');
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.message).toEqual('You have been successfully subscribed to subcategory SomeSubcategory');
  }));

  it('unsubscribeFromSubcategory() should unsubscribe me from a subcategory', fakeAsync(() => {
    let response: SubscriptionResponse;
    const mockResponse: SubscriptionResponse = {
      message: 'You have been successfully unsubscribed from subcategory SomeSubcategory'
    }
    
    subscriptionService.unsubscribeFromSubcategory('SomeSubcategory').subscribe(res => response = res);

    const request = httpMock.expectOne(subscriptionService.url + '/unsubscribeSubcategory/SomeSubcategory');
    expect(request.request.method).toBe('POST');
    request.flush(mockResponse);

    tick();

    expect(response).toBeDefined();
    expect(response.message).toEqual('You have been successfully unsubscribed from subcategory SomeSubcategory');
  }));
});
