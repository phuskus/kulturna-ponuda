import { Subscription } from 'rxjs';
import {
  AfterContentChecked,
  AfterContentInit,
  AfterViewInit,
  Component,
  HostListener,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ReviewDialogComponent } from './review-dialog/review-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Review } from 'src/app/shared/models/Review';
import { ReviewService } from 'src/app/services/review/review.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import {
  ActivatedRoute,
  NavigationStart,
  Router,
  NavigationEnd,
} from '@angular/router';
import {
  EmitEvent,
  EventBusService,
  Events,
} from 'src/app/services/event-bus/event-bus.service';
import Dialog from 'src/app/shared/dialog/Dialog';

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss'],
})
export class SingleOfferComponent
  implements OnInit, AfterContentInit, OnDestroy {
  offerId: number;
  offer: CulturalOffer;
  reviews: Review[] = [];
  currentReviewPage: number = 0;
  totalReviews: number = 0;
  pageSize: number = 5;
  isLastReviewPage: boolean = false;
  isReviewsLoading: boolean = false;
  previousRoute: string = '';

  private subscriptions: Subscription[] = [];

  public images: any = [
    { path: '../../assets/imgs/img1.jpg' },
    { path: '../../assets/imgs/img2.jpg' },
    { path: '../../assets/imgs/img3.jpg' },
    { path: '../../assets/imgs/img-1.jpg' },
  ];

  constructor(
    public dialog: MatDialog,
    public offerService: OfferService,
    public reviewService: ReviewService,
    private route: ActivatedRoute,
    private eventBus: EventBusService,
    private router: Router
  ) {
    this.offer = offerService.createEmpty();
    this.subscriptions.push(
      this.router.events
        .filter((e) => e instanceof NavigationEnd)
        .subscribe((e: NavigationEnd) => {
          const navigation = this.router.getCurrentNavigation();
          this.previousRoute = navigation.extras.state
            ? navigation.extras.state.previousRoute
            : this.previousRoute;
        })
    );
  }
  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  goBack(): void {
    this.router.navigateByUrl(this.previousRoute);
  }

  ngAfterContentInit(): void {
    this.subscriptions.push(
      this.route.params.subscribe((params) => {
        this.offerId = params.offerId;
        if (this.offerId === NaN) {
          // should redirect to 404
          throw new Error('Error 404, invalid id');
        }
        this.reviews = [];
        this.currentReviewPage = 0;
        this.totalReviews = 0;
        this.fetchReviews();
        this.fetchOffer();
      })
    );
    this.subscribeToScrollEvent();
  }

  resetFields() {
    this.reviews = [];
    this.isLastReviewPage = false;
    this.isReviewsLoading = false;
    this.currentReviewPage = 0;
    this.totalReviews = 0;
  }

  fetchOffer() {
    this.offerService.get(this.offerId).subscribe((data: CulturalOffer) => {
      this.offer = data;
      this.eventBus.emit(new EmitEvent(Events.OfferFocused, data));
    });
  }

  fetchReviews() {
    // increase before call so there cannot be two api calls with same page num
    this.currentReviewPage += 1;
    this.isReviewsLoading = true;
    this.reviewService
      .getForOfferId(this.offerId, this.currentReviewPage - 1, this.pageSize)
      .subscribe(
        (data) => {
          this.reviews = this.reviews.concat(data.content);
          this.isReviewsLoading = false;
          this.totalReviews = data.totalElements;
          if (data.totalPages == this.currentReviewPage) {
            this.isLastReviewPage = true;
          }
        },
        (err) => this.handleError(err)
      );
  }

  handleError(error: any) {
    console.log(error);
    this.isReviewsLoading = false;
    this.currentReviewPage -= 1;
  }

  subscribeToScrollEvent() {
    const x: Element = document.getElementsByTagName('mat-drawer')[0];
    x.addEventListener('scroll', (event: any) => {
      if (
        event.target.offsetHeight + event.target.scrollTop >=
        event.target.scrollHeight
      ) {
        this.scrolledToTheEndSoFetchNextPage();
      }
    });
  }

  scrolledToTheEndSoFetchNextPage() {
    if (!this.isLastReviewPage && !this.isReviewsLoading) this.fetchReviews();
  }

  openAddDialog(): void {
    const dialogRef = this.dialog.open(ReviewDialogComponent, {
      autoFocus: false,
      data: this.offer,
    });

    const sub = (dialogRef.componentInstance as ReviewDialogComponent).onSubscriptionCallBack.subscribe(
      (data) => {
        this.resetFields();
        this.fetchReviews();

        // since dialog is now closed, you can unsubscribe from its events
        sub.unsubscribe();
      }
    );
  }
}
