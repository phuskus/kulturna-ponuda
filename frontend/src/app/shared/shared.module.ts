import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StarRatingComponent } from './components/star-rating/star-rating.component';
import { ImageSelectorComponent } from './components/image-selector/image-selector.component';
import { SafeUrlPipe } from './pipes/safe-url.pipe';
import { MatIconModule } from '@angular/material/icon';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ImagePathExtractorComponent } from './components/image-path-extractor/image-path-extractor.component';

@NgModule({
  declarations: [StarRatingComponent, ImageSelectorComponent, SafeUrlPipe, ImagePathExtractorComponent],
  imports: [CommonModule, MatIconModule, FlexLayoutModule],
  exports: [StarRatingComponent, ImageSelectorComponent, SafeUrlPipe, ImagePathExtractorComponent],
})
export class SharedModule {}
