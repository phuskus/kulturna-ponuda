import { Component, Input, OnInit } from '@angular/core';
import { ImagePathExtractorComponent } from 'src/app/shared/components/image-path-extractor/image-path-extractor.component';
import { Picture } from 'src/app/shared/models/Picture';
import { Post } from 'src/app/shared/models/Post';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.scss'],
})
export class SinglePostComponent
  extends ImagePathExtractorComponent
  implements OnInit {
  @Input() public post: Post;

  constructor() {
    super();
  }

  ngOnInit(): void {
    this.post.pictures.forEach((picture) => {
      picture.path = super.getFullImgPath(picture);
    });
  }

  carouselCells() {
    return Math.min(3, this.post.pictures.length);
  }
}
