import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NavigationEnd, Router } from '@angular/router';
import { EmitEvent, EventBusService, Events } from 'src/app/services/event-bus/event-bus.service';
import { PostService } from 'src/app/services/post/post.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { Post } from 'src/app/shared/models/Post';
import { UpdatePostDialogComponent } from '../update-post-dialog/update-post-dialog.component';

@Component({
  selector: 'app-add-post-dialog',
  templateUrl: './add-post-dialog.component.html',
  styleUrls: ['./add-post-dialog.component.scss']
})
export class AddPostDialogComponent extends AddDialog<AddPostDialogComponent> {

  newObj: Post;
  postForm: FormGroup;
  currentRoute: string;
  filesSelected: FileList;

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<AddPostDialogComponent>,
    public service: PostService,
    @Inject(MAT_DIALOG_DATA) public offer: CulturalOffer,
    private eventBus: EventBusService,
    private router: Router
  ) {
    super(dialogRef, service);
    this.postForm = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]]
    })

   }

   get f() {
     return this.postForm.controls;
   }

   onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

   onSubmit() {
     this.newObj.culturalOffer = this.offer.id;
     this.newObj.title = this.postForm.value['title'];
     this.newObj.content = this.postForm.value['content'];
     this.service
      .addMultipart(this.newObj, this.filesSelected)
      .subscribe((data) => {
        this.eventBus.emit(new EmitEvent(Events.PostAdd, data));
        this.dialogRef.close();
      })

   }

}
