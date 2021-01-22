import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { OfferService } from 'src/app/services/offer/offer.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';
import { AddOfferDialogComponent } from '../offer-dialogs/add-offer-dialog/add-offer-dialog.component';
import { DescriptionDialogComponent } from '../offer-dialogs/description-dialog/description-dialog.component';
import { ImagesDialogComponent } from '../offer-dialogs/images-dialog/images-dialog.component';

@Component({
  selector: 'app-offer-table',
  templateUrl: './offer-table.component.html',
  styleUrls: ['./offer-table.component.scss']
})
export class OfferTableComponent extends AbstractDynamicPagingTable {

  constructor(
    public service: OfferService,
    public dialog: MatDialog
  ) { 
    super(service, dialog);
    this.tableColumns = ['name', 'category', 'region', 'city', 'address', 'avrageRating', 'description', 'pictures', 'post', 'actions'];
  }

  ngOnInit(): void {
  }

  openDescriptionDialog(row: CulturalOffer): void {
    this.openDialog(DescriptionDialogComponent, row);
  }

  openImagesDialog(row: CulturalOffer): void {
    this.openDialog(ImagesDialogComponent, row);
  }

  openAddOfferDialog() {
    this.openDialog(AddOfferDialogComponent);
  }

}
