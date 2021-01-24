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
import { ActivatedRoute, Router } from '@angular/router';
import Dialog from 'src/app/shared/dialog/Dialog';
import { UserService } from 'src/app/services/user/user.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Role } from 'src/app/shared/models/Role';

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
    public authService: AuthService,
    public router: Router,
    private route: ActivatedRoute
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
      this.fetchReviews();
      this.fetchOffer();
    });
    this.subscribeToScrollEvent();
  }

  resetFileds() {
    this.reviews = [];
    this.isLastReviewPage = false;
    this.isReviewsLoading = false;
    this.currentReviewPage = 0;
    this.totalReviews = 0;
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
    const drawer = document.getElementById('drawer');
    let header = document.getElementById('header');

    drawer.addEventListener('scroll', (event: any) => {
      if (
        event.target.offsetHeight + event.target.scrollTop >=
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

    const sub = (dialogRef.componentInstance as ReviewDialogComponent).onSubscriptionCallBack.subscribe(
      (data) => {
        this.resetFileds();
        this.fetchReviews();

        // since dialog is now closed, you can unsubscribe from its events
        sub.unsubscribe();
      }
    );
  }

  isActionDisabled() {
    return this.authService.getCurrentUserRole() == Role.ADMIN;
  }
}
