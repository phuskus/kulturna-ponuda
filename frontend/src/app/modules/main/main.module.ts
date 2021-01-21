import { NgxPaginationModule } from 'ngx-pagination';
import { NgModule, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapComponent } from './components/map/map.component';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
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
import { MatIconModule } from '@angular/material/icon';
import { MatRadioModule } from '@angular/material/radio';
import {MatPaginatorModule} from '@angular/material/paginator';


import { ResultsComponent } from './components/results/results.component';
import { SingleOfferComponent } from './components/single-offer/single-offer.component';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { SingleReviewComponent } from './components/single-review/single-review.component';
import { SearchComponent } from './components/search/search.component';
import { CategoryCardComponent } from './components/category-card/category-card.component';

import { FormsModule } from '@angular/forms';
import { ReviewDialogComponent } from './components/single-offer/review-dialog/review-dialog.component';
import { AvatarModule } from 'ngx-avatar';
import { SharedModule } from 'src/app/shared/shared.module';

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
  ],
  imports: [
    CommonModule,

    FormsModule,
    FlexLayoutModule,
    IvyCarouselModule,
    AvatarModule,
    SharedModule,

    MatCardModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    MatRadioModule,
    MatPaginatorModule,

    NgxPaginationModule,

    MainRoutingModule,
  ],
})
export class MainModule {}
