import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss'],
})
export class SingleOfferComponent implements OnInit {
  public images: any = [
    { path: '../../assets/imgs/img1.jpg' },
    { path: '../../assets/imgs/img2.jpg' },
    { path: '../../assets/imgs/img3.jpg' },
    { path: '../../assets/imgs/img-1.jpg' },
  ];

  public review: any = {
    name: 'Joe Biden',
    rating: 1,
    img: '../../assets/imgs/tr.jpg',
    text:
      "When I was younger, I didn't really understand modern art. Now as an Adult, I",
    images: [
      { path: 'https://s3-media0.fl.yelpcdn.com/bphoto/9d_3VbyQD3rM9fEnz8YC6w/o.jpg' },
      { path: 'https://s3-media0.fl.yelpcdn.com/bphoto/iQWXQYCNSoeDO7shXvbXEw/o.jpg' },
      { path: 'https://s3-media0.fl.yelpcdn.com/bphoto/Edby35itgkcFxzwMvLytDQ/o.jpg' },
      { path: 'https://s3-media0.fl.yelpcdn.com/bphoto/e6i4043nzXxx-7iqQ-Ohsg/o.jpg' },
    ],
  };
  constructor() {}

  ngOnInit(): void {}
}