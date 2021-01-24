import { Component, Input, OnInit } from '@angular/core';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Picture } from 'src/app/shared/models/Picture';
import { Post } from 'src/app/shared/models/Post';

@Component({
  selector: 'app-single-post',
  templateUrl: './single-post.component.html',
  styleUrls: ['./single-post.component.scss'],
})
export class SinglePostComponent
  implements OnInit {
  @Input() public post: Post;

  constructor(public pathService: PathExtractionService) {
  }

  ngOnInit(): void {
    this.post.pictures.forEach((picture) => {
      picture.path = this.pathService.getFullImgPath(picture.path);
    });
  }

  carouselCells() {
    return Math.min(3, this.post.pictures.length);
  }
}
