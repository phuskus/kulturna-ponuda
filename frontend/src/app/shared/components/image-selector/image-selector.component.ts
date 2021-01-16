import { Component, EventEmitter, Input, OnDestroy, Output } from '@angular/core';

@Component({
  selector: 'app-image-selector',
  templateUrl: './image-selector.component.html',
  styleUrls: ['./image-selector.component.scss'],
})
export class ImageSelectorComponent implements OnDestroy {
  @Input() multiple: boolean = false;

  @Output() newFilesEvent = new EventEmitter<FileList>();
  
  picturePaths: Array<string> = [];

  constructor() {}

  ngOnDestroy(): void {
    // destroy all created object urls to free memory
    this.picturePaths.forEach((path) => URL.revokeObjectURL(path));
  }

  onFilesSelected(event): void {
    const filesSelected: FileList = event.target.files;
    this.createPictureURLsAndSetPicturePaths(filesSelected);

    // emit all files selected by the user to the parent component
    this.newFilesEvent.emit(filesSelected);
  }

  createPictureURLsAndSetPicturePaths(files: FileList) {
    this.picturePaths = [];
    Array.from(files).forEach((file) =>
      this.picturePaths.push(URL.createObjectURL(file))
    );
  }
}
