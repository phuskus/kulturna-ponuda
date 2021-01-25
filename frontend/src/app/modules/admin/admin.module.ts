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
import { MatMenuModule } from '@angular/material/menu';
import { SharedModule } from 'src/app/shared/shared.module';
import { CategoryTableComponent } from './components/categories/category-table/category-table.component';
import { AddCategoryDialogComponent } from './components/categories/category-dialogs/add-category-dialog/add-category-dialog.component';
import { DeleteCategoryDialogComponent } from './components/categories/category-dialogs/delete-category-dialog/delete-category-dialog.component';
import { UpdateCategoryDialogComponent } from './components/categories/category-dialogs/update-category-dialog/update-category-dialog.component';
import { AddSubcategoryDialogComponent } from './components/subcategories/subcategory-dialogs/add-subcategory-dialog/add-subcategory-dialog.component';
import { DeleteSubcategoryDialogComponent } from './components/subcategories/subcategory-dialogs/delete-subcategory-dialog/delete-subcategory-dialog.component';
import { UpdateSubcategoryDialogComponent } from './components/subcategories/subcategory-dialogs/update-subcategory-dialog/update-subcategory-dialog.component';
import { OfferTableComponent } from './components/offers/offer-table/offer-table.component';
import { DescriptionDialogComponent } from './components/offers/offer-dialogs/description-dialog/description-dialog.component';
import { AddOfferDialogComponent } from './components/offers/offer-dialogs/add-offer-dialog/add-offer-dialog.component';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { AddressAutocompleteComponent } from './components/offers/address-autocomplete/address-autocomplete.component';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { UpdateOfferDialogComponent } from './components/offers/offer-dialogs/update-offer-dialog/update-offer-dialog.component';
import { DeleteOfferDialogComponent } from './components/offers/offer-dialogs/delete-offer-dialog/delete-offer-dialog.component';
import { PostTableComponent } from './components/posts/post-table/post-table.component';
import { ContentDialogComponent } from './components/posts/post-dialogs/content-dialog/content-dialog.component';
import { PicturesComponent } from './components/pictures/pictures.component';
import { UpdatePostDialogComponent } from './components/posts/post-dialogs/update-post-dialog/update-post-dialog.component';
import { DeletePostDialogComponent } from './components/posts/post-dialogs/delete-post-dialog/delete-post-dialog.component';
import { AddPostDialogComponent } from './components/posts/post-dialogs/add-post-dialog/add-post-dialog.component';
import { SubcategoryTableComponent } from './components/subcategories/subcategory-table/subcategory-table.component';


@NgModule({
  declarations: [
    AdminPage,
    ReviewTableComponent,
    AdminTableComponent,
    DeleteReviewDialogComponent,
    AddAdminDialogComponent,
    DeleteReviewDialogComponent,
    DeleteAdminDialogComponent,
    CategoryTableComponent,
    AddCategoryDialogComponent,
    DeleteCategoryDialogComponent,
    UpdateCategoryDialogComponent,
    SubcategoryTableComponent,
    AddSubcategoryDialogComponent,
    DeleteSubcategoryDialogComponent,
    UpdateSubcategoryDialogComponent,
    OfferTableComponent,
    DescriptionDialogComponent,
    AddOfferDialogComponent,
    AddressAutocompleteComponent,
    UpdateOfferDialogComponent,
    DeleteOfferDialogComponent,
    PostTableComponent,
    ContentDialogComponent,
    PicturesComponent,
    UpdatePostDialogComponent,
    DeletePostDialogComponent,
    AddPostDialogComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,

    FlexLayoutModule,
    FormsModule,
    SharedModule,

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
    IvyCarouselModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatSelectModule,
    HttpClientModule,
    MatFormFieldModule,
    MatTooltipModule,
    MatSnackBarModule
  ],
})
export class AdminModule {}
