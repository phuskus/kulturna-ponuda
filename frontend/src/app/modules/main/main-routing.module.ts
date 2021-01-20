import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoAuthGuard } from 'src/app/services/auth/guards/no-auth.guard';
import { RoleGuard } from 'src/app/services/auth/guards/role.guard';
import { Role } from 'src/app/shared/models/Role';
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
      {
        path: 'offers', component: SidebarComponent, children: [
          { path: 'search', component: ResultsComponent },
          { path: ':offerId', component: SingleOfferComponent } //, canActivate: [NoAuthGuard, RoleGuard],
          //data: { roles: [Role.USER]}}
        ]
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule { }
