import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavigationExtras, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
 
import { SearchComponent } from './search.component';
 
describe('SearchComponent', () => {
  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;
 
  let router: Router;
 
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate'),
    };
 
    TestBed.configureTestingModule({
      declarations: [SearchComponent],
      imports: [RouterTestingModule],
      providers: [{ provide: Router, useValue: routerMock }],
    }).compileComponents();
 
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
 
    router = TestBed.inject(Router);
  });
 
  it('should create', () => {
    expect(component).toBeTruthy();
  });
 
  it('should navigate to results page with query', () => {
    component.searchQuery = 'test';
    const navigationExtras: NavigationExtras = {
      queryParams: { query: 'test' },
      queryParamsHandling: 'merge',
    };
    component.onSubmit();
    expect(router.navigate).toHaveBeenCalledWith(
      ['/offers/search'],
      navigationExtras
    );
  });
});