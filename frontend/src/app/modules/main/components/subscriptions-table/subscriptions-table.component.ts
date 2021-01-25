import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as moment from 'moment';
import { AbstractDynamicPagingTable } from 'src/app/modules/admin/components/table/AbstractDynamicPagingTable';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { Subscription } from 'src/app/shared/models/Subscription';

@Component({
  selector: 'app-subscriptions-table',
  templateUrl: './subscriptions-table.component.html',
  styleUrls: ['./subscriptions-table.component.scss']
})
export class SubscriptionsTableComponent extends AbstractDynamicPagingTable {
  constructor(public service: SubscriptionService, public dialog: MatDialog) {
    super(service, dialog);
    this.tableColumns = [
      'name',
      'date',
      'actions'
    ]
  }

  ngOnInit(): void {}

  unsubscribe(subscription: Subscription) {
    this.service.delete(subscription.id).subscribe(subscription => {
      console.log('Deleted subscription ' + subscription.id);
      this.updateTable();
    }, error => {
      console.log('Delete subscription failed!');
      console.log(error);
      this.updateTable();
    });
  }

  toMomentDate(isoDate: string) {
    return moment(isoDate).format('DD.MM.YYYY');
  }

}
