import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from 'src/app/services/post/post.service';
import DeleteDialog from 'src/app/shared/dialog/DeleteDialog';
import { Post } from 'src/app/shared/models/Post';

@Component({
  selector: 'app-delete-post-dialog',
  templateUrl: './delete-post-dialog.component.html',
  styleUrls: ['./delete-post-dialog.component.scss'],
})
export class DeletePostDialogComponent extends DeleteDialog<DeletePostDialogComponent>  {
  constructor(
    public dialogRef: MatDialogRef<DeletePostDialogComponent>,
    public service: PostService,
    @Inject(MAT_DIALOG_DATA) public data: Post
  ) {
    super(dialogRef, service, data);
  }
}
