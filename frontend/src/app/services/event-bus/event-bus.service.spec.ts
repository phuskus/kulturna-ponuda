import { TestBed } from '@angular/core/testing';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { Post } from 'src/app/shared/models/Post';

import { EmitEvent, EventBusService, Events } from './event-bus.service';

describe('EventBusServiceService', () => {
  let service: EventBusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventBusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should subscribe to PostAdd event and recieve data', (done) => {
    const testPost: Post = {
      culturalOffer: 1,
      title: 'Title',
      pictures: [],
      offerName: 'Offer',
      id: 1,
      datePosted: new Date(),
      content: 'Test content',
    };

    service.on(Events.PostAdd, (testData: Post) => {
      expect(testData).toEqual(testPost);
      done();
    });
    service.emit(new EmitEvent(Events.PostAdd, testPost));
  });

  it('should subscribe to OfferListChange event and recieve data', (done) => {
    const offerList: CulturalOffer[] = [
      {
        id: 1,
        address: 'Test address 14',
        admin: 1,
        averageRating: 2,
        category: 1,
        categoryName: 'Test category',
        city: 'Novi Sad',
        description: 'Test description',
        latitude: 44.0,
        longitude: 44.0,
        name: 'Test offer',
        pictures: [],
        posts: [],
        region: 'Vojvodina',
        reviews: [],
      },
    ];

    service.on(Events.OfferListChange, (testData: CulturalOffer[]) => {
      expect(testData).toEqual(offerList);
      done();
    });
    service.emit(new EmitEvent(Events.OfferListChange, offerList));
  });

  it('should subscribe to OfferFocused event and recieve data', (done) => {
    const offer: CulturalOffer = {
      id: 1,
      address: 'Test address 14',
      admin: 1,
      averageRating: 2,
      category: 1,
      categoryName: 'Test category',
      city: 'Novi Sad',
      description: 'Test description',
      latitude: 44.0,
      longitude: 44.0,
      name: 'Test offer',
      pictures: [],
      posts: [],
      region: 'Vojvodina',
      reviews: [],
    };

    service.on(Events.OfferFocused, (testData: CulturalOffer) => {
      expect(testData).toEqual(offer);
      done();
    });
    service.emit(new EmitEvent(Events.OfferFocused, offer));
  });

  it('should subscribe to RouteChange event and recieve data', (done) => {
    const route: string = '/'

    service.on(Events.RouteChange, (testData: string) => {
      expect(testData).toEqual(route);
      done();
    });
    service.emit(new EmitEvent(Events.RouteChange, route));
  });
});
