import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageSelectorComponent } from './image-selector.component';

describe('ImageSelectorComponent', () => {
  let component: ImageSelectorComponent;
  let fixture: ComponentFixture<ImageSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ImageSelectorComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit files on filesSelected', () => {
    let selectedFiles: FileList;
    component.newFilesEvent.subscribe((files) => (selectedFiles = files));

    const blob = new Blob([''], { type: 'text/html' });
    blob['lastModifiedDate'] = '';
    blob['name'] = 'filename';
    const file = <File>blob;
    const fileList = {
      0: file,
      1: file,
      length: 2,
      item: (index: number) => file,
    };

    let event = {
      target: { files: fileList },
    };
    component.onFilesSelected(event);
    expect(selectedFiles).toEqual(fileList);
  });
});
