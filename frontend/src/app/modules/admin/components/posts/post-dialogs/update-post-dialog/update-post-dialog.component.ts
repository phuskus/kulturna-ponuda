import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from 'src/app/services/message/message.service';
import { PostService } from 'src/app/services/post/post.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { Post } from 'src/app/shared/models/Post';

@Component({
  selector: 'app-update-post-dialog',
  templateUrl: './update-post-dialog.component.html',
  styleUrls: ['./update-post-dialog.component.scss'],
})
export class UpdatePostDialogComponent extends UpdateDialog<UpdatePostDialogComponent> {
  newObj: Post;
  postForm: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<UpdatePostDialogComponent>,
    public service: PostService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    @Inject(MAT_DIALOG_DATA) public data: Post
  ) {
    super(dialogRef, service, snackbar, messageService, data);
    this.postForm = this.fb.group({
      title: [this.newObj.title, [Validators.required]],
      content: [this.newObj.content, [Validators.required]],
    });
  }

  get f() {
    return this.postForm.controls;
  }

  onSubmit() {
    this.newObj.title = this.postForm.value['title'];
    this.newObj.content = this.postForm.value['content'];
    this.service.update(this.newObj.id, this.newObj).subscribe(
      (data) => {
        this.onSubscriptionCallBack.emit(data);
        this.snackbarSuccess('Successfully updated Post');
        this.dialogRef.close();
      },
      (err) => this.snackbarError('Failed to update Post!')
    );
  }
}
