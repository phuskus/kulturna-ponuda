import {
  EmitEvent,
  EventBusService,
  Events,
} from './../../../../services/event-bus/event-bus.service';
import { PageParams } from '../../../../shared/models/PageParams';
import { CulturalOffer } from '../../../../shared/models/CulturalOffer';
import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  NavigationStart,
  ParamMap,
  Router,
} from '@angular/router';
import { MatMenuTrigger } from '@angular/material/menu';
import { OfferService } from 'src/app/services/offer/offer.service';
import Page from 'src/app/shared/models/Page';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Subscription } from 'rxjs/Rx';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss'],
})
export class ResultsComponent
  implements OnInit, OnDestroy {
  @ViewChild(MatMenuTrigger) filterMenuTrigger: MatMenuTrigger;

  public loading: boolean = true;

  public category: string = '';
  public query: string = '';

  public page: number = 1;
  public pageSize: number = 10;
  public sortBy: string = 'id';
  public descending: boolean = false;

  public count = 0;

  public sortCriteria = [
    { name: 'None', value: 'id', selected: true },
    { name: 'Name', value: 'name', selected: false },
    { name: 'Description', value: 'description', selected: false },
    { name: 'Address', value: 'address', selected: false },
    { name: 'City', value: 'city', selected: false },
    { name: 'Region', value: 'region', selected: false },
  ];

  public regions = [
    { name: 'Vojvodina', checked: false },
    { name: 'Central Serbia', checked: false },
    { name: 'Eastern Serbia', checked: false },
    { name: 'Western Serbia', checked: false },
    { name: 'Southern Serbia', checked: false },
  ];

  public cities = [
    { name: 'Belgrade', checked: false },
    { name: 'Novi Sad', checked: false },
    { name: 'Kragujevac', checked: false },
    { name: 'Leskovac', checked: false },
    { name: 'Novi Pazar', checked: false },
    { name: 'Niš', checked: false },
    { name: 'Kraljevo', checked: false },
    { name: 'Zrenjanin', checked: false },
    { name: 'Kruševac', checked: false },
    { name: 'Subotica', checked: false },
    { name: 'Smederevo', checked: false },
    { name: 'Valjevo', checked: false },
    { name: 'Bor', checked: false },
    { name: 'Čačak', checked: false },
    { name: 'Sombor', checked: false },
    { name: 'Vranje', checked: false },
    { name: 'Zaječar', checked: false },
    { name: 'Negotin', checked: false },
    { name: 'Užice', checked: false },
  ].sort();


  public offers: CulturalOffer[] = [];
  private currentRoute: string = '';

  private subscriptions: Subscription[] = [];

  subscribeState: string = 'loading';
  isLoggedIn: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private offerService: OfferService,
    public pathService: PathExtractionService,
    private eventBus: EventBusService,
    private router: Router,
    private authService: AuthService,
    private subscriptionService: SubscriptionService
  ) {
    this.subscriptions.push(
      this.router.events
        .filter(e => e instanceof NavigationEnd)
        .subscribe((e: NavigationEnd) => {
          this.currentRoute = e.url;
          this.eventBus.emit(new EmitEvent(Events.RouteChange, e.url));
        })
    );
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.route.queryParamMap.subscribe((paramMap) => {
        const params = paramMap['params'];
        if (params['category'] != undefined) {
          this.category = params['category'];
        }
        if (params['query'] != undefined) {
          this.query = params['query'];
        }
        this.fetchOffers();
      })
    );
    
    this.isLoggedIn = this.authService.getCurrentUser() != undefined;

    if (!this.isLoggedIn) {
      this.subscribeState = 'not subscribed';
    } else if (this.category) {
      this.subscriptionService.getIsSubscribedToSubcategory(this.category).subscribe((data) => {
        if (data.subscribed) {
          this.subscribeState = 'subscribed';
        } else {
          this.subscribeState = 'not subscribed';
        }
      }, (error) => {
        console.log('Failed to get isSubscribedToSubcategory');
        console.log(error);
      });
    }

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  getPageParams(): PageParams {
    let params: PageParams = {
      pageNo: this.page - 1,
      pageSize: this.pageSize,
      sortBy: this.sortBy,
      descending: this.descending,
    };
    return params;
  }

  fetchOffers() {
    let regionNames = this.regions
      .filter((r) => r.checked)
      .map((r) => r.name)
      .join(',');
    let cityNames = this.cities
      .filter((c) => c.checked)
      .map((c) => c.name)
      .join(',');
    return this.offerService
      .searchFilterCulturalOffers(
        this.getPageParams(),
        this.category,
        this.query,
        regionNames,
        cityNames
      )
      .subscribe((res: Page<CulturalOffer>) => {
        this.loading = false;
        this.offers = res.content;
        this.eventBus.emit(new EmitEvent(Events.OfferListChange, res.content));
        this.count = res.totalElements;
      });
  }

  clearRegions(event?) {
    if (event) {
      event.stopPropagation();
    }
    this.regions.forEach((region) => (region.checked = false));
  }

  clearCities(event?) {
    if (event) {
      event.stopPropagation();
    }
    this.cities.forEach((city) => (city.checked = false));
  }

  cancelFilter() {
    this.clearRegions();
    this.clearCities();
  }

  cancelSort() {
    this.descending = false;
    this.sortBy = 'id';
  }

  changeSortDirection(event) {
    event.stopPropagation();
    this.descending = !this.descending;
  }

  handlePageChange(event): void {
    this.page = event;
    this.fetchOffers();
  }

  subscribe() {
    if (!this.isLoggedIn) {
      this.router.navigateByUrl('/login');
      return;
    }
    
    this.subscribeState = 'loading';
    this.subscriptionService.subscribeToSubcategory(this.category).subscribe(() => {
      this.subscribeState = 'subscribed';
    }, (error) => {
      console.log('Failed to subscribe to subcategory');
      console.log(error);
    });
  }

  unsubscribe() {
    this.subscribeState = 'loading';
    this.subscriptionService.unsubscribeFromSubcategory(this.category).subscribe(() => {
      this.subscribeState = 'not subscribed';
    }, (error) => {
      console.log('Failed to unsubscribe from subcategory');
      console.log(error);
    });
  }
}
