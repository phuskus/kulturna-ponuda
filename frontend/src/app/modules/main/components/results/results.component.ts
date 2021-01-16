import { PageParams } from './../../../../model/PageParams';
import { CulturalOffer } from './../../../../model/CulturalOffer';
import { OfferService } from './../../../../services/offer/offer.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss'],
})
export class ResultsComponent implements OnInit {
  public subcatId : string = this.route.snapshot.paramMap.get('subcatId');  

  public pageParams : PageParams;

  public offers : CulturalOffer[];

  public result: any = {
    id: 2,
    name: 'Museum of Modern Art',
    location: 'Dunavska 35, Novi Sad',
    category: 'Museum of art & history',
    image: '../../../../../assets/imgs/ill.jpg',
    description:
      'The museum is open, and many exhibits are available. Unfortunately, the planetarium is closed. The rainforest sphere and the shake house require special reseratiaos and a 2-4 hour wait. Sadly, not worth going during COVID, I would wait until restrictions are lifted to get the ful experience.',
  };
  constructor(private route : ActivatedRoute, private offerService : OfferService) {}

  ngOnInit(): void {}

  fetchCategories() {
    return this.offerService.getCulturalOffersByCategory(this.subcatId, this.pageParams).subscribe((res: CulturalOffer[]) => {
      this.offers = res;
    })
  }
}
