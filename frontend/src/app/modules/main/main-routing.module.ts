import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CardbarComponent } from './components/cardbar/cardbar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
    children: [
      { path: '', component: CardbarComponent },
      { path: 'offers', component: SidebarComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
