import { Subscription } from 'rxjs';
import { AfterContentInit, Component, OnDestroy, OnInit } from '@angular/core';
import { ReviewDialogComponent } from './review-dialog/review-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Review } from 'src/app/shared/models/Review';
import { ReviewService } from 'src/app/services/review/review.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Role } from 'src/app/shared/models/Role';
import {
  EmitEvent,
  EventBusService,
  Events,
} from 'src/app/services/event-bus/event-bus.service';
import { Post } from 'src/app/shared/models/Post';
import { PostService } from 'src/app/services/post/post.service';
import Page from 'src/app/shared/models/Page';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { SubscriptionResponse } from 'src/app/shared/models/SubscriptionResponse';
import { IsSubscribedResponse } from 'src/app/shared/models/IsSubscribedResponse';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss'],
})
export class SingleOfferComponent implements AfterContentInit, OnDestroy {
  offerId: number;
  offer: CulturalOffer;
  previousRoute: string = '';

  reviews: Review[] = [];
  currentReviewPage: number = 0;
  totalReviews: number = 0;
  pageSizeReviews: number = 5;
  isLastReviewPage: boolean = false;
  isReviewsLoading: boolean = false;
  subscribeState: string = 'loading';
  isLoggedIn: boolean = false;
  offerExists: boolean = true;

  posts: Post[] = [];
  currentPostPage: number = 1;
  totalPosts: number = 0;
  pageSizePosts: number = 3;

  private subscriptions: Subscription[] = [];

  constructor(
    public dialog: MatDialog,
    public offerService: OfferService,
    public reviewService: ReviewService,
    public authService: AuthService,
    public router: Router,
    private route: ActivatedRoute,
    private postService: PostService,
    private eventBus: EventBusService,
    public subscriptionService: SubscriptionService,
    public pathService: PathExtractionService
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

  ngAfterContentInit(): void {
    this.subscriptions.push(
      this.route.params.subscribe((params) => {
        this.offerId = params.offerId;
        this.reviews = [];
        this.currentReviewPage = 0;
        this.totalReviews = 0;
        this.fetchOffer();
        this.fetchReviews();
        this.fetchPosts();
      })
    );
    this.subscribeToScrollEvent();

    this.isLoggedIn = this.authService.getCurrentUser() != undefined;

    if (!this.isLoggedIn) {
      this.subscribeState = 'not subscribed';
      return;
    }

    this.subscriptionService.getIsSubscribedToOffer(this.offerId).subscribe(
      (data: IsSubscribedResponse) => {
        if (data.subscribed) {
          this.subscribeState = 'subscribed';
        } else {
          this.subscribeState = 'not subscribed';
        }
      },
      (error) => {
        console.log('Failed to get isSubscribedToOffer');
        console.log(error);
      }
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  goBack(): void {
    this.router.navigateByUrl(this.previousRoute);
  }

  resetFields() {
    this.reviews = [];
    this.isLastReviewPage = false;
    this.isReviewsLoading = false;
    this.currentReviewPage = 0;
    this.totalReviews = 0;
  }

  fetchOffer() {
    this.offerService.get(this.offerId).subscribe(
      (data: CulturalOffer) => {
        if (data === undefined) this.offerExists = false;
        else {
          this.offerExists = true;
          data.pictures.forEach((picture) => {
            picture.path = this.pathService.getFullImgPath(picture.path);
          });
          this.offer = data;
        }

        this.eventBus.emit(new EmitEvent(Events.OfferFocused, data));
      },
      (err) => {
        this.offerExists = false;
        throw new Error('bad offer');
      }
    );
  }

  fetchPosts() {
    this.postService
      .getForOfferId(this.offerId, this.currentPostPage - 1, this.pageSizePosts)
      .subscribe((data: Page<Post>) => {
        this.posts = data.content;
        this.totalPosts = data.totalElements;
      });
  }

  fetchReviews() {
    // increase before call so there cannot be two api calls with same page num
    this.currentReviewPage += 1;
    this.isReviewsLoading = true;
    this.reviewService
      .getForOfferId(
        this.offerId,
        this.currentReviewPage - 1,
        this.pageSizeReviews
      )
      .subscribe(
        (data: Page<Review>) => {
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

  handlePageChange(event: number): void {
    this.currentPostPage = event;
    this.fetchPosts();
  }

  subscribeToScrollEvent() {
    const drawer = document.getElementById('drawer');
    let header = document.getElementById('header');

    drawer?.addEventListener('scroll', (event: any) => {
      if (
        event.target.offsetHeight + event.target.scrollTop + 50 >=
        event.target.scrollHeight
      ) {
        this.scrolledToTheEndSoFetchNextPage();
      }

      if (event.target.scrollTop > 15) header.classList.add('sticky');
      else header.classList.remove('sticky');
    });
  }

  scrolledToTheEndSoFetchNextPage() {
    if (!this.isLastReviewPage && !this.isReviewsLoading) this.fetchReviews();
  }

  openAddDialog(): void {
    if (!this.authService.getCurrentUser()) {
      this.router.navigateByUrl('/login');
      return;
    }

    const dialogRef = this.dialog.open(ReviewDialogComponent, {
      autoFocus: false,
      data: this.offer,
    });

    const sub = (dialogRef?.componentInstance as ReviewDialogComponent)?.onSubscriptionCallBack.subscribe(
      (data) => {
        this.resetFields();
        this.fetchReviews();

        // since dialog is now closed, you can unsubscribe from its events
        sub.unsubscribe();
      }
    );
  }

  isActionDisabled() {
    return this.authService.getCurrentUserRole() == Role.ADMIN;
  }

  subscribe() {
    if (!this.isLoggedIn) {
      this.router.navigateByUrl('/login');
      return;
    }

    this.subscribeState = 'loading';
    this.subscriptionService.subscribeToOffer(this.offerId).subscribe(
      (response: SubscriptionResponse) => {
        this.subscribeState = 'subscribed';
      },
      (error) => {
        console.log(error);
      }
    );
  }

  unsubscribe() {
    this.subscribeState = 'loading';
    this.subscriptionService.unsubscribeFromOffer(this.offerId).subscribe(
      (response: SubscriptionResponse) => {
        this.subscribeState = 'not subscribed';
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
