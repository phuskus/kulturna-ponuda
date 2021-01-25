import { Injectable } from '@angular/core';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import { Picture } from 'src/app/shared/models/Picture';

@Injectable({
  providedIn: 'root',
})
export class PathExtractionService {
  getFullImgPath(path: string): string {
    return AppSettings.API_ENDPOINT + path;
  }
}
