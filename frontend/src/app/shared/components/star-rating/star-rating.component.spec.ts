import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { StarRatingComponent } from './star-rating.component';

describe('StarRatingComponent', () => {
  let component: StarRatingComponent;
  let fixture: ComponentFixture<StarRatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StarRatingComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StarRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set new value when clicked on star', () => {
    let selectedRating: number;
    component.newRatingEvent.subscribe(
      (rating: number) => (selectedRating = rating)
    );

    let star = fixture.debugElement.query(By.css('mat-icon'));
    star.triggerEventHandler('click', null);
    expect(selectedRating).toEqual(component.rating);
  });

  it('should set value when clicked on nth-star', () => {
    let wantedRating = 5;

    let star = fixture.debugElement.queryAll(By.css('mat-icon'))[wantedRating - 1];
    star.triggerEventHandler('click', null);
    expect(component.rating).toEqual(wantedRating);
  });
});
