import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminPage } from './pages/admin-page/admin.page';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [AdminPage],
  imports: [
    CommonModule,
    AdminRoutingModule,

    FlexLayoutModule,

    MatCardModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    MatDialogModule,
    MatIconModule,
    MatToolbarModule,
    MatListModule,
    MatDividerModule,
  ],
})
export class AdminModule {}
