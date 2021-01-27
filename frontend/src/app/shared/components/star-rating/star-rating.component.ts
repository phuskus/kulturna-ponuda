import { Component, EventEmitter, Input, Output } from '@angular/core';

const goldColor: string = '#FABB05';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.scss'],
})
export class StarRatingComponent {
  @Input() public maxRating: number = 5;
  @Input() public rating: number = 0;
  @Input() public color: string = goldColor;
  @Input() public size: number = 40;
  @Input() public readonly: boolean = false;
  @Input() public gap: string = "5px";

  @Output() public newRatingEvent = new EventEmitter<number>();

  public rangeArray: Array<number> = [];

  constructor() {
    this.rangeArray = this.createArrayFromOneToMaxRating();
  }

  createArrayFromOneToMaxRating() {
    let rangeArray: Array<number> = [];
    for (let i = 1; i <= this.maxRating; i++) rangeArray.push(i);
    return rangeArray;
  }

  onStarClick(value: number): void {
    if (this.readonly) return;

    if (value === this.rating) value = value - 1;

    this.rating = value;
    this.newRatingEvent.emit(value);
  }

  formatRating(): number {
    return Math.round(this.rating);
  }
}
