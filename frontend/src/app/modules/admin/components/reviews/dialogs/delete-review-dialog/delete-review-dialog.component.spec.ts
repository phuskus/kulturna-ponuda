import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { ReviewService } from 'src/app/services/review/review.service';
import { Review } from 'src/app/shared/models/Review';

import { DeleteReviewDialogComponent } from './delete-review-dialog.component';

describe('DeleteReviewDialogComponent', () => {
  let component: DeleteReviewDialogComponent;
  let fixture: ComponentFixture<DeleteReviewDialogComponent>;
  let service: ReviewService;
  let dialog: MatDialogRef<Review>;
  let dialogData: Review;

  beforeEach(async () => {
    let serviceMock = {
      delete: jasmine.createSpy('delete').and.returnValue(of({})),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };

    let reviewMock = {
      id: 1,
      rating: 3,
      content: '',
      user: { id: 0, name: 'Peter', surname: 'Peter' },
      datePosted: new Date(),
      culturalOfferId: 0,
      culturalOfferName: '',
      pictures: [],
    };

    await TestBed.configureTestingModule({
      declarations: [DeleteReviewDialogComponent],
      providers: [
        { provide: ReviewService, useValue: serviceMock },
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: reviewMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteReviewDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ReviewService);
    dialog = TestBed.inject(MatDialogRef);
    dialogData = TestBed.inject(MAT_DIALOG_DATA);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
