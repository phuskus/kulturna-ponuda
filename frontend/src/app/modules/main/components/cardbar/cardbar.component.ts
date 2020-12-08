import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cardbar',
  templateUrl: './cardbar.component.html',
  styleUrls: ['./cardbar.component.scss'],
})
export class CardbarComponent implements OnInit {
  public offers: any = [
    { name: 'Festivals', img: '../../assets/imgs/fest.png' },
    { name: 'Galleries', img: '../../assets/imgs/gal.jpg' },
    { name: 'Museums', img: '../../assets/imgs/mus.png' },
    { name: 'Monuments', img: '../../assets/imgs/mon.jpg' },
  ];
  
  constructor() {}

  ngOnInit(): void {}
}
