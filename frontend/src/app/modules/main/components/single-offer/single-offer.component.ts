import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-single-offer',
  templateUrl: './single-offer.component.html',
  styleUrls: ['./single-offer.component.scss']
})
export class SingleOfferComponent implements OnInit {
  public images:any = [
    {path: 'http://ivylab.space/assets/photo-1524324463413-57e3d8392df1.jpg'},
    {path: 'http://ivylab.space/assets/photo-1548625149-d37da68f9a7f.jpg'},
    {path: 'http://ivylab.space/assets/photo-1489365091240-6a18fc761ec2.jpg'},
    {path: 'http://ivylab.space/assets/photo-1547691889-841a6f1c5ca6.jpg'},
  ]
  constructor() { }

  ngOnInit(): void {
  }

}
