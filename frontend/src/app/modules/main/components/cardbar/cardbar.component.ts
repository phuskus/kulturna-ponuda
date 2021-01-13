import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cardbar',
  templateUrl: './cardbar.component.html',
  styleUrls: ['./cardbar.component.scss'],
})
export class CardbarComponent implements OnInit {
  public categories: any = [
    {
      name: "Category 1",
      subcats: [
        { name: 'Festivals', img: '../../assets/imgs/fest.png', path: 'festival' },
        { name: 'Galleries', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Monuments', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Museums', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Fairs', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Festivals', img: '../../assets/imgs/mus.png', path: 'museum' },
      ]
    },
    {
      name: "Category 2",
      subcats: [
        { name: 'Museums', img: '../../assets/imgs/mus.png', path: 'museum' },
        { name: 'Fairs', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Museums', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Monuments', img: '../../assets/imgs/mon.jpg', path: 'monument' },
      ]
    },
    {
      name: "Category 3",
      subcats: [
        { name: 'Museums', img: '../../assets/imgs/mus.png', path: 'museum' },
        { name: 'Fairs', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
        { name: 'Monuments', img: '../../assets/imgs/mon.jpg', path: 'monument' },
      ]
    },
  ];
  
  constructor() {}

  ngOnInit(): void {}
}
