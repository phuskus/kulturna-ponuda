import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
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
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Post
  ) {
    super(dialogRef, service, snackbar, messageService, data);
  }
}
