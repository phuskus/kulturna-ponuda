import { HttpClient } from '@angular/common/http';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import Page from 'src/app/shared/models/Page';
import { Post } from 'src/app/shared/models/Post';

import { PostService } from './post.service';

describe('PostService', () => {
  let service: PostService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostService],
    });
    service = TestBed.inject(PostService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get for offer id', fakeAsync(() => {
    let posts: Post[];

    const mockPosts: Page<Post> = {
      content: [
        {
          content: 'Test Content1',
          culturalOffer: 1,
          datePosted: new Date(),
          id: 1,
          offerName: 'Test offer',
          pictures: [],
          title: 'Test title',
        },
        {
          content: 'Test Content2',
          culturalOffer: 1,
          datePosted: new Date(),
          id: 1,
          offerName: 'Test offer',
          pictures: [],
          title: 'Test title',
        },
        {
          content: 'Test Content3',
          culturalOffer: 1,
          datePosted: new Date(),
          id: 1,
          offerName: 'Test offer',
          pictures: [],
          title: 'Test title',
        },
      ],
      totalPages: 1,
      totalElements: 3,
    };

    service.getForOfferId(1, 0, 5).subscribe((data) => {
      posts = data.content;
      expect(data.totalPages).toBe(1);
      expect(data.totalElements).toBe(3);
    });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT + 'posts/offer/1?pageNo=0&pageSize=5'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);

    tick();

    expect(posts.length).toBe(3);
    expect(posts[0].content).toBe('Test Content1');
    expect(posts[1].content).toBe('Test Content2');
    expect(posts[2].content).toBe('Test Content3');
  }));

  it('should get none for offer id', fakeAsync(() => {
    let posts: Post[];

    const mockPosts: Page<Post> = {
      content: [],
      totalPages: 1,
      totalElements: 0,
    };

    service.getForOfferId(2, 0, 5).subscribe((data) => {
      posts = data.content;
      expect(data.totalPages).toBe(1);
      expect(data.totalElements).toBe(0);
    });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT + 'posts/offer/2?pageNo=0&pageSize=5'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockPosts);

    tick();

    expect(posts.length).toBe(0);
  }));

  it('should add new post with no images', fakeAsync(() => {
    let newPost: Post = {
        content: 'Test Content',
        culturalOffer: 1,
        datePosted: new Date(),
        id: -1,
        offerName: 'Test offer',
        pictures: [],
        title: 'Test title',
    };
    const mockPost: Post = {
      content: 'Test Content',
      culturalOffer: 1,
      datePosted: new Date(),
      id: 1,
      offerName: 'Test offer',
      pictures: [],
      title: 'Test title',
    };

    service.addMultipart(newPost, null).subscribe((data) => {
      newPost = data;
    });

    const req = httpMock.expectOne(
      AppSettings.API_ENDPOINT + 'posts'
    );
    expect(req.request.method).toBe('POST');
    req.flush(mockPost);

    tick();

    expect(newPost.id).toBe(1);
    expect(newPost.content).toBe('Test Content');
    expect(newPost.offerName).toBe('Test offer');
    expect(newPost.title).toBe('Test title');
    expect(newPost.culturalOffer).toBe(1);
  }));
});
