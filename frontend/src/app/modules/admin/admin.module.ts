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
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatPaginatorModule} from '@angular/material/paginator';



import { AdminRoutingModule } from './admin-routing.module';
import { AdminPage } from './pages/admin-page/admin.page';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReviewTableComponent } from './components/reviews/review-table/review-table.component';
import { ReviewDialogComponent } from './components/reviews/review-dialog/review-dialog.component';
import { AdminTableComponent } from './components/admins/admin-table/admin-table.component';

@NgModule({
  declarations: [AdminPage, ReviewTableComponent, ReviewDialogComponent, AdminTableComponent],
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
    MatTableModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
  ],
})
export class AdminModule {}
