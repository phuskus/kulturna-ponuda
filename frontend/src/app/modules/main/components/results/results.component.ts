import { PageParams } from './../../../../model/PageParams';
import { CulturalOffer } from './../../../../model/CulturalOffer';
import { OfferService } from './../../../../services/offer/offer.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CulturalOfferPage } from 'src/app/model/CulturalOfferPage';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss'],
})
export class ResultsComponent implements OnInit {
  public loading: boolean = true;

  public page: number = 1;
  public pageSize: number = 10;
  public sortBy: string = "id";
  public descending: boolean = false;

  public count = 0;

  public offers: CulturalOffer[] = [];

  constructor(private route: ActivatedRoute, private offerService: OfferService) { }

  ngOnInit(): void {
    this.fetchOffers();
  }

  getRequestParams(): PageParams {
    let params: PageParams = {
      pageNo: this.page - 1,
      pageSize: this.pageSize,
      sortBy: this.sortBy,
      descending: this.descending
    };
    return params;
  }

  fetchOffers() {
    this.route.queryParamMap.subscribe((paramMap) => {
      const params = paramMap['params'];
      if (params['category']) {
        const categoryId: string = params['category'];
        return this.offerService.getCulturalOffersByCategory(categoryId, this.getRequestParams()).subscribe((res: CulturalOfferPage) => {
          this.loading = false;
          this.offers = res.content;
          this.count = res.totalElements;
        })
      } else if (params['query']) {
        const query: string = params['query'];
        return this.offerService.getCulturalOffersByQuery(query, this.getRequestParams()).subscribe((res: CulturalOfferPage) => {
          this.loading = false;
          this.offers = res.content;
          this.count = res.totalElements;
        })
      }
    });
  }
  
  handlePageChange(event): void {
    this.page = event;
    this.fetchOffers();
  }
}
