import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from 'src/app/services/post/post.service';
import Dialog from 'src/app/shared/dialog/Dialog';
import { Post } from 'src/app/shared/models/Post';

@Component({
  selector: 'app-content-dialog',
  templateUrl: './content-dialog.component.html',
  styleUrls: ['./content-dialog.component.scss'],
})
export class ContentDialogComponent<T> extends Dialog<T> {
  constructor(
    public dialogRef: MatDialogRef<T>,
    public service: PostService,
    @Inject(MAT_DIALOG_DATA) public data: Post
  ) {
    super(dialogRef, service);
  }

  onSubmit() {
    console.log('submit');
  }
}
