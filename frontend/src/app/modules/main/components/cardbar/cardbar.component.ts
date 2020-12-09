import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cardbar',
  templateUrl: './cardbar.component.html',
  styleUrls: ['./cardbar.component.scss'],
})
export class CardbarComponent implements OnInit {
  public offers: any = [
    { name: 'Festivals', img: '../../assets/imgs/fest.png', path: 'festival' },
    { name: 'Galleries', img: '../../assets/imgs/gal.jpg', path: 'gallery' },
    { name: 'Museums', img: '../../assets/imgs/mus.png', path: 'museum' },
    { name: 'Monuments', img: '../../assets/imgs/mon.jpg', path: 'monument' },
  ];
  
  constructor() {}

  ngOnInit(): void {}
}
