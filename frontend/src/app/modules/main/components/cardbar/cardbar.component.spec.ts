import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardbarComponent } from './cardbar.component';

describe('CardbarComponent', () => {
  let component: CardbarComponent;
  let fixture: ComponentFixture<CardbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CardbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
