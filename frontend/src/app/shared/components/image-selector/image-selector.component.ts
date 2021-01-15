import { Component, EventEmitter, OnDestroy, Output } from '@angular/core';

@Component({
  selector: 'app-image-selector',
  templateUrl: './image-selector.component.html',
  styleUrls: ['./image-selector.component.scss'],
})
export class ImageSelectorComponent implements OnDestroy {
  @Output() newFilesEvent = new EventEmitter<Array<string>>();
  picturePaths: Array<string> = [];
  constructor() {}

  ngOnDestroy(): void {
    // destroy all created object urls to free memory
    for (let path of this.picturePaths) URL.revokeObjectURL(path);
  }

  onFilesSelected(event): void {
    const filesSelected = event.target.files;

    this.picturePaths = [];
    for (let picture of filesSelected) {
      this.picturePaths.push(URL.createObjectURL(picture));
    }

    // emit all files selected by the user to the parent component
    this.newFilesEvent.emit(filesSelected);
  }
}
