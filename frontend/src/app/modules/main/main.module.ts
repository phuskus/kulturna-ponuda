import { NgModule } from '@angular/core';
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

@NgModule({
  declarations: [
    MapComponent,
    SidebarComponent,
    CardbarComponent,
    LandingPageComponent,
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    MatCardModule,
    MatButtonModule,

    MainRoutingModule,

    NgxMapboxGLModule.withConfig({
      accessToken:
        'pk.eyJ1Ijoic3N0ZWYiLCJhIjoiY2thMDEzMXBpMGNpYjNmcG11Y2ozYTlucCJ9.GDzoIBfJMXOLfL1vxMuGnw',
    }),
  ],
  // exports: [LandingComponent, CardbarComponent, SidebarComponent],
})
export class MainModule {}
