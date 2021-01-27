import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavigationEnd, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Map } from 'leaflet';
import { Observable, of } from 'rxjs';
import {
  EmitEvent,
  EventBusService,
  Events,
} from 'src/app/services/event-bus/event-bus.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';

import { MapComponent } from './map.component';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;
  let eventBusService: EventBusService;
  let router: Router;

  let initMapSpy: jasmine.Spy;
  let addMarkersSpy: jasmine.Spy;

  beforeEach(async () => {
    const focusedOffer: CulturalOffer = {
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

    const eventBusMock = {
      on: jasmine.createSpy('on').and.callThrough(),
      emit: jasmine.createSpy('emit').and.callThrough(),
    };

    const ne = new NavigationEnd(0, '/', '/');

    const routerMock = {
      navigate: jasmine.createSpy('navigate'),
      events: {
        filter: (fn: any) =>
          new Observable((observer) => {
            observer.next(ne);
            observer.complete();
          }),
      },
    };

    TestBed.configureTestingModule({
      declarations: [MapComponent],
      imports: [RouterTestingModule],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: EventBusService, useValue: eventBusMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    });

    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    eventBusService = TestBed.inject(EventBusService);

    initMapSpy = spyOn(component, 'initMap').and.callThrough();
    addMarkersSpy = spyOn(component, 'addMarkers').and.callThrough();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should subscribe to router events in constructor', () => {
    expect(component).toBeTruthy();
    expect(component.subscriptions.length).toBe(1);
  });

  it('should subscribe to event bus in onInit', (done) => {
    component.ngOnInit();
    fixture.whenStable().then(() => {
      expect(component.subscriptions.length).toBe(3);
      expect(eventBusService.on).toHaveBeenCalledTimes(2);
      done();
    });
  });

  it('should init map in afterViewInit', (done) => {
    component.ngAfterViewInit();
    fixture.whenStable().then(() => {
      expect(initMapSpy).toHaveBeenCalled();
      expect(component.map).toBeTruthy();
      done();
    });
  });

  it('should add markers to list', (done) => {
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
        latitude: 45.0,
        longitude: 45.0,
        name: 'Test offer',
        pictures: [],
        posts: [],
        region: 'Vojvodina',
        reviews: [],
      },
      {
        id: 2,
        address: 'Test address 28',
        admin: 1,
        averageRating: 2,
        category: 1,
        categoryName: 'Test category',
        city: 'Novi Sad',
        description: 'Test description',
        latitude: 40.0,
        longitude: 40.0,
        name: 'Test offer 2',
        pictures: [],
        posts: [],
        region: 'Vojvodina',
        reviews: [],
      },
    ];
    component.ngOnInit();
    component.ngAfterViewInit();
    component.addMarkers(offerList);
    fixture.whenStable().then(() => {
      expect(component.markers.length).toBe(2);
      done();
    });
  });

  it('should navigate to single offer page on marker click', () => {
    component.ngOnInit();
    component.ngAfterViewInit();
    const event: any = {
      sourceTarget: {
        options: {
          attribution: '1',
        },
      },
    };
    component.previousResultsPage = 'test';
    component.offerClick(event);
    expect(router.navigate).toHaveBeenCalledWith(['/offers/1'], {
      state: { previousRoute: 'test' },
    });
  });

  it('should show offer details on offer mouse over', () => {
    component.ngOnInit();
    component.ngAfterViewInit();
    const event: any = {
      containerPoint: {
        x: 1,
        y: 1
      },
      sourceTarget: {
        options: {
          attribution: '1',
        },
      },
    };
    component.offerList = [
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
    component.offerMouseOver(event);
    expect(component.displayInfo).toEqual('visible');
    component.offerMouseOut(event);
    expect(component.displayInfo).toEqual('hidden');
  });
});
