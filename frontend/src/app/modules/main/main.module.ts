import { NgxPaginationModule } from 'ngx-pagination';
import { NgModule, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from './components/map/map.component';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CardbarComponent } from './components/cardbar/cardbar.component';
import { MainRoutingModule } from './main-routing.module';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatInputModule } from '@angular/material/input';
import { ResultsComponent } from './components/results/results.component';
import { SingleOfferComponent } from './components/single-offer/single-offer.component';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { RatingModule } from 'ng-starrating';
import { SingleReviewComponent } from './components/single-review/single-review.component';
import { SearchComponent } from './components/search/search.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';

import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    MapComponent,
    SidebarComponent,
    CardbarComponent,
    LandingPageComponent,
    ResultsComponent,
    SingleOfferComponent,
    SingleReviewComponent,
    SearchComponent,
    CategoryCardComponent
  ],
  imports: [
    CommonModule,

    FormsModule,
    FlexLayoutModule,
    IvyCarouselModule,
    RatingModule,

    MatCardModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    NgxPaginationModule,

    MainRoutingModule,

    NgxMapboxGLModule.withConfig({
      accessToken:
        'pk.eyJ1Ijoic3N0ZWYiLCJhIjoiY2thMDEzMXBpMGNpYjNmcG11Y2ozYTlucCJ9.GDzoIBfJMXOLfL1vxMuGnw',
    }),
  ],
})
export class MainModule {}
