import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { NgxPaginationModule } from 'ngx-pagination';
import { Observable, of } from 'rxjs';
import { AuthService } from 'src/app/services/auth/auth.service';
import { EventBusService } from 'src/app/services/event-bus/event-bus.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { PostService } from 'src/app/services/post/post.service';
import { ReviewService } from 'src/app/services/review/review.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';

import { SingleOfferComponent } from './single-offer.component';

describe('SingleOfferComponent', () => {
  let component: SingleOfferComponent;
  let fixture: ComponentFixture<SingleOfferComponent>;
  let dialog: MatDialog;
  let offerService: OfferService;
  let reviewService: ReviewService;
  let authService: AuthService;
  let router: Router;
  let route: ActivatedRoute;
  let postService: PostService;
  let eventBus: EventBusService;
  let subscriptionService: SubscriptionService;

  beforeEach(async () => {
    let offerServiceSpy = {
      get: jasmine.createSpy('get').and.returnValue(of({})),
      createEmpty: jasmine.createSpy('createEmpty'),
    };

    let reviewServiceSpy = {
      getForOfferId: jasmine.createSpy('getForOfferId').and.returnValue(of({})),
    };

    let postServiceSpy = {
      getForOfferId: jasmine.createSpy('getForOfferId').and.returnValue(of({})),
    };

    let subscriptionServiceSpy = {
      getIsSubscribedToOffer: jasmine
        .createSpy('getIsSubscribedToOffer')
        .and.returnValue(of({ subscribed: true })),
      subscribeToOffer: jasmine
        .createSpy('subscribeToOffer')
        .and.returnValue(of({})),
      unsubscribeFromOffer: jasmine
        .createSpy('unsubscribeFromOffer')
        .and.returnValue(of({})),
    };

    let routerSpy = {
      navigateByUrl: jasmine.createSpy('navigateByUrl'),
      events: {
        filter(fn: any): Observable<Event> {
          return of(new Event('string'));
        },
      },
      getCurrentNavigation() {
        return {
          extras: {},
        };
      },
    };

    let routeMock = {
      params: of({ oferId: 2 }),
    };

    let dialogSpy = {
      open: jasmine.createSpy('open'),
    };

    let eventBusSpy = {
      emit: jasmine.createSpy(),
    };

    TestBed.configureTestingModule({
      declarations: [SingleOfferComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        NgxPaginationModule,
      ],
      providers: [
        { provide: OfferService, useValue: offerServiceSpy },
        { provide: ReviewService, useValue: reviewServiceSpy },
        { provide: PostService, useValue: postServiceSpy },
        { provide: SubscriptionService, useValue: subscriptionServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: ActivatedRoute, useValue: routeMock },
        { provide: EventBusService, useValue: eventBusSpy },
        { provide: MatDialog, useValue: dialogSpy },
      ],
    });

    fixture = TestBed.createComponent(SingleOfferComponent);
    component = fixture.componentInstance;
    offerService = TestBed.inject(OfferService);
    reviewService = TestBed.inject(ReviewService);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
    postService = TestBed.inject(PostService);
    eventBus = TestBed.inject(EventBusService);
    subscriptionService = TestBed.inject(SubscriptionService);
    dialog = TestBed.inject(MatDialog);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch offer, reviews and posts on init', () => {
    component.ngAfterContentInit();

    expect(offerService.get).toHaveBeenCalled();
    expect(reviewService.getForOfferId).toHaveBeenCalled();
    expect(postService.getForOfferId).toHaveBeenCalled();
    expect(subscriptionService.getIsSubscribedToOffer).toHaveBeenCalled();
  });

  it('should navigate to log in when user not signed in and clicks write review', () => {
    spyOn(authService, 'getCurrentUser').and.returnValue(undefined);
    component.ngAfterContentInit();
    component.openAddDialog();

    expect(router.navigateByUrl).toHaveBeenCalledOnceWith('/login');
  });

  it('should open dialog when user is logged in and clicks write review', () => {
    spyOn(authService, 'getCurrentUser').and.returnValue({});

    component.openAddDialog();

    expect(router.navigateByUrl).not.toHaveBeenCalled();
    expect(dialog.open).toHaveBeenCalled();
  });

  it('should navigate to login when user not signed and clicks subscribe', () => {
    component.isLoggedIn = false;
    component.subscribe();
    expect(router.navigateByUrl).toHaveBeenCalledOnceWith('/login');
  });

  it('should subscribe when user signed and clicks subscribe', () => {
    component.isLoggedIn = true;
    component.subscribe();

    expect(subscriptionService.subscribeToOffer).toHaveBeenCalled();
  });

  it('should unsubscribe when user signed, subscribed and clicks unsubscribe', () => {
    component.isLoggedIn = true;
    component.unsubscribe();

    expect(subscriptionService.unsubscribeFromOffer).toHaveBeenCalled();
  });
});
