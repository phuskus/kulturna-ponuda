import { Component, OnInit } from '@angular/core';
import { ReviewDialogComponent } from './review-dialog/review-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Review } from 'src/app/shared/models/Review';

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss'],
})
export class SingleOfferComponent implements OnInit {
  constructor(public dialog: MatDialog) {}

  openAddDialog(): void {
    this.dialog.open(ReviewDialogComponent, {
      autoFocus: false,
      data: {id: 0, name: 'Museum of Modern Art'},
    });
  }

  ngOnInit(): void {}

  public images: any = [
    { path: '../../assets/imgs/img1.jpg' },
    { path: '../../assets/imgs/img2.jpg' },
    { path: '../../assets/imgs/img3.jpg' },
    { path: '../../assets/imgs/img-1.jpg' },
  ];

  public review: Review = {
    id: 0,
    user: { id: 0, name: 'Joe Biden', username: '', password: '' },
    rating: 1,
    culturalOfferId: 0,
    culturalOfferName: 'Museum of Modern Art',
    content:
      "When I was younger, I didn't really understand modern art. Now as an Adult, I",
    pictures: [
      {
        path:
          'https://s3-media0.fl.yelpcdn.com/bphoto/9d_3VbyQD3rM9fEnz8YC6w/o.jpg',
        id: 0,
        placeholder: '',
        image: '',
      },
      {
        path:
          'https://s3-media0.fl.yelpcdn.com/bphoto/iQWXQYCNSoeDO7shXvbXEw/o.jpg',
        id: 0,
        placeholder: '',
        image: '',
      },
      {
        path:
          'https://s3-media0.fl.yelpcdn.com/bphoto/Edby35itgkcFxzwMvLytDQ/o.jpg',
        id: 0,
        placeholder: '',
        image: '',
      },
      {
        path:
          'https://s3-media0.fl.yelpcdn.com/bphoto/e6i4043nzXxx-7iqQ-Ohsg/o.jpg',
        id: 0,
        placeholder: '',
        image: '',
      },
    ],
  };
}
