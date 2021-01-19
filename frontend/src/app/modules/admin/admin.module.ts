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
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatPaginatorModule } from '@angular/material/paginator';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminPage } from './pages/admin-page/admin.page';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReviewTableComponent } from './components/reviews/review-table/review-table.component';
import { AdminTableComponent } from './components/admins/admin-table/admin-table.component';
import { DeleteReviewDialogComponent } from './components/reviews/dialogs/delete-review-dialog/delete-review-dialog.component';
import { AddAdminDialogComponent } from './components/admins/admin-dialogs/add-admin-dialog/add-admin-dialog.component';
import { DeleteAdminDialogComponent } from './components/admins/admin-dialogs/delete-admin-dialog/delete-admin-dialog.component';
import { FormsModule } from '@angular/forms';
import { UpdateAdminDialogComponent } from './components/admins/admin-dialogs/update-admin-dialog/update-admin-dialog.component';
import {MatMenuModule} from '@angular/material/menu';


@NgModule({
  declarations: [
    AdminPage,
    ReviewTableComponent,
    AdminTableComponent,
    DeleteReviewDialogComponent,
    AddAdminDialogComponent,
    DeleteReviewDialogComponent,
    DeleteAdminDialogComponent,
    UpdateAdminDialogComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,

    FlexLayoutModule,
    FormsModule,

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
    MatMenuModule,
  ],
})
export class AdminModule {}
