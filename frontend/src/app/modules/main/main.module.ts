import { NgxPaginationModule } from 'ngx-pagination';
import { NgModule, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from './components/map/map.component';
import { NgxMapboxGLModule } from 'ngx-mapbox-gl';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MatCardModule } from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatMenuModule } from '@angular/material/menu';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CardbarComponent } from './components/cardbar/cardbar.component';
import { MainRoutingModule } from './main-routing.module';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatInputModule } from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';


import { ResultsComponent } from './components/results/results.component';
import { SingleOfferComponent } from './components/single-offer/single-offer.component';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { SingleReviewComponent } from './components/single-review/single-review.component';
import { SearchComponent } from './components/search/search.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';
import { ImageSelectorComponent } from 'src/app/shared/components/image-selector/image-selector.component';


import { FormsModule } from '@angular/forms';
import { ReviewDialogComponent } from './components/single-offer/review-dialog/review-dialog.component';
import { SafeUrlPipe } from 'src/app/shared/pipes/safe-url.pipe';
import { AvatarModule } from 'ngx-avatar';
import { StarRatingComponent } from 'src/app/shared/components/star-rating/star-rating.component';

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
    CategoryCardComponent,
    ReviewDialogComponent,
    ImageSelectorComponent,
    SafeUrlPipe,
    StarRatingComponent,
  ],
  imports: [
    CommonModule,

    FormsModule,
    FlexLayoutModule,
    IvyCarouselModule,
    AvatarModule,

    MatCardModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    
    NgxPaginationModule,

    MainRoutingModule,

    NgxMapboxGLModule.withConfig({
      accessToken:
        'pk.eyJ1Ijoic3N0ZWYiLCJhIjoiY2thMDEzMXBpMGNpYjNmcG11Y2ozYTlucCJ9.GDzoIBfJMXOLfL1vxMuGnw',
    }),
  ],
})
export class MainModule { }
