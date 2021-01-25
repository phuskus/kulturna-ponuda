import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EventBusService, Events } from 'src/app/services/event-bus/event-bus.service';
import { PostService } from 'src/app/services/post/post.service';
import { Post } from 'src/app/shared/models/Post';
import { PicturesComponent } from '../../pictures/pictures.component';
import { AbstractDynamicPagingTable } from '../../table/AbstractDynamicPagingTable';
import { ContentDialogComponent } from '../post-dialogs/content-dialog/content-dialog.component';
import { DeletePostDialogComponent } from '../post-dialogs/delete-post-dialog/delete-post-dialog.component';
import { UpdatePostDialogComponent } from '../post-dialogs/update-post-dialog/update-post-dialog.component';

@Component({
  selector: 'app-post-table',
  templateUrl: './post-table.component.html',
  styleUrls: ['./post-table.component.scss']
})
export class PostTableComponent extends AbstractDynamicPagingTable {

  constructor(public service: PostService, 
    public dialog: MatDialog,
    private eventBus: EventBusService) {
    super(service, dialog);
    this.tableColumns = [
      'offerName',
      'datePosted',
      'title',
      'content',
      'pictures',
      'actions'
    ];
  }

  ngOnInit(): void {
    this.eventBus.on(Events.PostAdd, (post: Post) => {
      console.log(post);
      this.updateTable();
    })
  }

  openContentDialog(row: Post): void {
    this.openDialog(ContentDialogComponent, row);
  }

  openPictureDialog(row: Post): void {
    this.openDialog(PicturesComponent, row);
  }

  openUpdatePostDialog(row: Post) {
    this.openDialog(UpdatePostDialogComponent, row);
  }

  openDeleteDialog(row: Post): void {
    this.openDialog(DeletePostDialogComponent, row);
  }

}
