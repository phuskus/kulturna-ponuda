import {
  AfterContentChecked,
  AfterContentInit,
  AfterViewInit,
  Component,
  HostListener,
  OnInit,
} from '@angular/core';
import { ReviewDialogComponent } from './review-dialog/review-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Review } from 'src/app/shared/models/Review';
import { ReviewService } from 'src/app/services/review/review.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { ActivatedRoute } from '@angular/router';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss'],
})
export class SingleOfferComponent implements AfterContentInit {
  offerId: number;
  offer: CulturalOffer;
  reviews: Review[] = [];
  currentReviewPage: number = 0;
  totalReviews: number = 0;
  pageSize: number = 5;
  isLastReviewPage: boolean = false;
  isReviewsLoading: boolean = false;
  subscribeState: string = "loading";
  isLoggedIn: boolean = false;

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
    public subscriptionService: SubscriptionService,
    public authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.offer = offerService.createEmpty();
  }

  ngAfterContentInit(): void {
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
    });
    this.subscribeToScrollEvent();

    this.subscriptionService.getIsSubscribed(this.offerId).subscribe((data) => {
      if (data) {
        this.subscribeState = "subscribed";
      } else {
        this.subscribeState = "not subscribed";
      }
    });

    this.isLoggedIn = this.authService.getCurrentUser() != undefined
  }

  fetchOffer() {
    this.offerService.get(this.offerId).subscribe((data) => {
      this.offer = data as CulturalOffer;
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
      } else {
      }
    });
  }

  scrolledToTheEndSoFetchNextPage() {
    if (!this.isLastReviewPage && !this.isReviewsLoading) this.fetchReviews();
  }

  openAddDialog(): void {
    this.dialog.open(ReviewDialogComponent, {
      autoFocus: false,
      data: { id: 0, name: 'Museum of Modern Art' },
    });
  }

  subscribe() {
    if (!this.isLoggedIn) {
      this.router.navigateByUrl('/login');
      return;
    }
    
    this.subscribeState = 'loading';
    this.subscriptionService.subscribeToOffer(this.offerId).subscribe((responseMessage) => {
      this.subscribeState = 'subscribed';
    });
  }

  unsubscribe() {
    this.subscribeState = 'loading';
    this.subscriptionService.unsubscribeFromOffer(this.offerId).subscribe((responseMessage) => {
      this.subscribeState = 'not subscribed';
    });
  }
}
