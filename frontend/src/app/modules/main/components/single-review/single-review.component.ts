import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-single-review',
  templateUrl: './single-review.component.html',
  styleUrls: ['./single-review.component.scss']
})
export class SingleReviewComponent implements OnInit {
  @Input() public review: any;

  constructor() { }

  ngOnInit(): void {
  }

}
