import { Component } from '@angular/core';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { Picture } from '../../models/Picture';

@Component({
  template: '',
})
export class ImagePathExtractorComponent {
  getFullImgPath(picture: Picture): string {
    return AppSettings.API_ENDPOINT + picture.path;
  }
}
