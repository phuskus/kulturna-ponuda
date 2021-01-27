import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PathExtractionService } from 'src/app/services/path-extraction/path-extraction.service';
import { Post } from 'src/app/shared/models/Post';

import { SinglePostComponent } from './single-post.component';

describe('SinglePostComponent', () => {
  let component: SinglePostComponent;
  let fixture: ComponentFixture<SinglePostComponent>;
  let pathService: PathExtractionService;

  beforeEach(() => {
    let pathServiceMock = {
      getFullImgPath: jasmine
        .createSpy('getFullImgPath')
        .and.returnValue(of('https://i.imgur.com/MVwP2rI.jpg')),
    };

    TestBed.configureTestingModule({
      declarations: [SinglePostComponent],
      providers: [
        { provide: PathExtractionService, useValue: pathServiceMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(SinglePostComponent);
    component = fixture.componentInstance;
    pathService = TestBed.inject(PathExtractionService);
    let post: Post = {
      id: 1,
      title: 'Test Title',
      content: 'Test Content',
      culturalOffer: 1,
      offerName: 'Test Offer',
      datePosted: new Date(),
      pictures: [
        {
          id: 1,
          placeholder: 'Placeholder',
          path: 'https://i.imgur.com/MVwP2rI.jpg',
          image: '',
        },
        {
          id: 2,
          placeholder: 'Placeholder2',
          path: 'https://i.imgur.com/MVwP2rI.jpg',
          image: '',
        },
      ],
    };
    component.post = post;

    //fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should have input info', () => {
    expect(component.post).toBeTruthy();
  });

  it('should execute init and call full path', () => {
    expect(component.post.pictures.length).toBe(2);
    component.ngOnInit();

    fixture.whenStable().then(() => {
      expect(pathService.getFullImgPath).toHaveBeenCalledTimes(2);
    });
  });
});
