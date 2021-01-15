import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.scss'],
})
export class StarRatingComponent {
  @Input() maxRating: number = 5;
  @Input() rating: number = 0;
  @Input() color: string = '#FABB05';
  @Input() size: number = 40;
  @Input() readonly: boolean = false;

  @Output() newRatingEvent = new EventEmitter<number>();

  rangeArray: Array<number> = [];
  constructor() {
    for (let i = 1; i <= this.maxRating; i++) this.rangeArray.push(i);
  }

  onStarClick(value: number): void {
    if (this.readonly) return;

    if (value === this.rating) value = value - 1;

    this.rating = value;
    this.newRatingEvent.emit(value);
  }
}
