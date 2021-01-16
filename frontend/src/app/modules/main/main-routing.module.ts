import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoAuthGuard } from 'src/app/services/auth/guards/no-auth.guard';
import { RoleGuard } from 'src/app/services/auth/guards/role.guard';
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
        {path: 'search/:query', component: ResultsComponent},
        {path: ':id', component: SingleOfferComponent }//, canActivate: [NoAuthGuard, RoleGuard],
        //data: { roles: ['ADMIN']}}
      ]},
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
