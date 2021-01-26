import { EventBusService } from './../../../../../services/event-bus/event-bus.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Events } from 'src/app/services/event-bus/event-bus.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { PicturesComponent } from '../../pictures/pictures.component';
import { AddPostDialogComponent } from '../../posts/post-dialogs/add-post-dialog/add-post-dialog.component';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';
import { AddOfferDialogComponent } from '../offer-dialogs/add-offer-dialog/add-offer-dialog.component';
import { DeleteOfferDialogComponent } from '../offer-dialogs/delete-offer-dialog/delete-offer-dialog.component';
import { DescriptionDialogComponent } from '../offer-dialogs/description-dialog/description-dialog.component';
import { UpdateOfferDialogComponent } from '../offer-dialogs/update-offer-dialog/update-offer-dialog.component';
import { Post } from 'src/app/shared/models/Post';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-offer-table',
  templateUrl: './offer-table.component.html',
  styleUrls: ['./offer-table.component.scss'],
})
export class OfferTableComponent extends AbstractDynamicPagingTable implements OnInit, OnDestroy {
  constructor(public service: OfferService, public dialog: MatDialog, private eventBus: EventBusService) {
    super(service, dialog);
    this.tableColumns = [
      'name',
      'category',
      'region',
      'city',
      'address',
      'avrageRating',
      'description',
      'pictures',
      'post',
      'actions',
    ];
  }

  private eventSubscription: Subscription;

  ngOnInit(): void {
    this.eventSubscription = this.eventBus.on(Events.PostAdd, (post: Post) => {
      this.dialog.closeAll();
    })
  }
  
  ngOnDestroy(): void {
    this.eventSubscription.unsubscribe();
  }

  openAddPost(row: CulturalOffer): void {
    this.openDialog(AddPostDialogComponent, row);
  }

  openDescriptionDialog(row: CulturalOffer): void {
    this.openDialog(DescriptionDialogComponent, row);
  }

  openPicturesDialog(row: CulturalOffer): void {
    this.openDialog(PicturesComponent, row);
  }

  openAddOfferDialog() {
    this.openDialog(AddOfferDialogComponent);
  }

  openUpdateOfferDialog(row: CulturalOffer) {
    this.openDialog(UpdateOfferDialogComponent, row);
  }

  openDeleteDialog(row: CulturalOffer): void {
    this.openDialog(DeleteOfferDialogComponent, row);
  }
}
