import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { of } from 'rxjs';
import { ReviewService } from 'src/app/services/review/review.service';
import { UserService } from 'src/app/services/user/user.service';
import { Review } from 'src/app/shared/models/Review';

import { ReviewDialogComponent } from './review-dialog.component';

describe('ReviewDialogComponent', () => {
  let component: ReviewDialogComponent;
  let fixture: ComponentFixture<ReviewDialogComponent>;
  let service: ReviewService;
  let userService: UserService;
  let dialog: MatDialogRef<Review>;
  let dialogData: Review;

  beforeEach(async () => {
    let serviceMock = {
      add: jasmine.createSpy('add').and.returnValue(of({})),
      createEmpty: jasmine.createSpy('createEmpty').and.returnValue({}),
    };

    let userServiceMock = {
      getLoggedUser: jasmine.createSpy('getLoggedUser').and.returnValue(
        of({
          id: 0,
          name: '',
          surname: '',
        })
      ),
    };

    let dialogMock = {
      close: jasmine.createSpy('close'),
    };
    await TestBed.configureTestingModule({
      declarations: [ReviewDialogComponent],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: ReviewService, useValue: serviceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: MatDialogRef, useValue: dialogMock },
        { provide: MAT_DIALOG_DATA, useValue: { id: 1, name: 'fdsa' } },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewDialogComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(ReviewService);
    userService = TestBed.inject(UserService);
    dialog = TestBed.inject(MatDialogRef);
    dialogData = TestBed.inject(MAT_DIALOG_DATA);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
