import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardbarComponent } from './components/cardbar/cardbar.component';
import { ResultsComponent } from './components/results/results.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { SingleOfferComponent } from './components/single-offer/single-offer.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
    children: [
      { path: '', component: CardbarComponent },
      { path: 'offers', component: SidebarComponent, children: [
        {path: 'search/:name', component: ResultsComponent},
        {path: ':id', component: SingleOfferComponent}
      ] },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
