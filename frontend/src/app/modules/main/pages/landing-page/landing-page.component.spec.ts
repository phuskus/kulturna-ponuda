import { HttpClient } from '@angular/common/http';
import { async, ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService, SnackbarColors } from 'src/app/services/message/message.service';
import { SubscriptionService } from 'src/app/services/subscription/subscription.service';
import { UserService } from 'src/app/services/user/user.service';
import { PasswordDialogComponent } from '../../components/account/password-dialog/password-dialog.component';
import { of } from 'rxjs'
import { LandingPageComponent } from './landing-page.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
/*
describe('LandingPageComponent', () => {
  let component: LandingPageComponent;
  let fixture: ComponentFixture<LandingPageComponent>;
  let router: any;
  let authService: any;
  let userService: any;
  let messageService: any;
  let subscriptionService: any;
  let snackBar: any;
  let dialog: any;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LandingPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    let routerMock = {
      navigate: jasmine.createSpy('navigate'),
      navigateByUrl: jasmine.createSpy('navigateByUrl')
    };

    let authServiceMock = {
      checkIfAdmin: jasmine.createSpy('checkIfAdmin')
        .and.returnValue(false),
      logout: jasmine.createSpy('logout')
    };

    let userServiceMock = {
      get: jasmine.createSpy('get').and.returnValue(of( {
        body: {
          name: 'Some',
          surname: 'User',
          username: 'someUsername123',
          password: 'some pass hash'
        } 
       }))
    };

    let messageServiceMock = {
      openSnackBar: jasmine.createSpy('openSnackBar')
    };

    let subscriptionServiceMock = {
      getPage: jasmine.createSpy('getPage')
    };

    let snackBarMock = {

    };

    let dialogMock = {
      open: jasmine.createSpy('open')
    };

    TestBed.configureTestingModule({
      declarations: [ LandingPageComponent ],
      providers: [
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: UserService, useValue: userServiceMock },
        { provide: MessageService, useValue: messageServiceMock },
        { provide: SubscriptionService, useValue: subscriptionServiceMock },
        { provide: MatSnackBar, useValue: snackBarMock },
        { provide: MatDialog, useValue: dialogMock },
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });

    fixture = TestBed.createComponent(LandingPageComponent);
    component = fixture.componentInstance;
    
    router = TestBed.inject(Router);
    authService = TestBed.inject(AuthService);
    userService = TestBed.inject(UserService);
    messageService = TestBed.inject(MessageService);
    subscriptionService = TestBed.inject(SubscriptionService);
    snackBar = TestBed.inject(MatSnackBar);
    dialog = TestBed.inject(MatDialog);

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check if the current user is admin', fakeAsync(() => {
    component.ngOnInit();
    expect(authService.checkIfAdmin).toHaveBeenCalled();
  }));

  it('should call logout, navigateByUrl and openSnackBar', fakeAsync(() => {
    component.logout();
    expect(authService.logout).toHaveBeenCalled();
    expect(router.navigateByUrl).toHaveBeenCalledWith('/', { skipLocationChange: true });
    expect(messageService.openSnackBar).toHaveBeenCalledWith(snackBar, 'Successfully logged out!', 'End', 5000, SnackbarColors.SUCCESS);
  }));

  it('should call get user', fakeAsync(() => {
    localStorage['currentUser'] = of({"id":11,"token":"eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImthaXRseW4uamFzdEB5YWhvby5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTE3NzU3NzAsImV4cCI6MTYxMTgxODk3MH0.iTjzKPT_GX55vJoDTVlsFF_wapHnBICuo2j3I1Tz-10sCBolG2YzKHEAVcDkU5U5F6xzonsLKp6yPkgRDctZGA","expiresIn":43200000,"userRole":"USER"});
    component.onEditProfile();
    expect(userService.get).toHaveBeenCalled();
  }));

  it('should call get subscriptions', fakeAsync(() => {
    component.onEditSubscriptions();
    expect(subscriptionService.getPage).toHaveBeenCalled();
    expect(dialog.open).toHaveBeenCalled();
  }));

  it('should open the dialog', fakeAsync(() => {
    component.onChangePassword();
    expect(dialog.open).toHaveBeenCalledWith(PasswordDialogComponent, {
      autoFocus: false,
      data: {}
    });
  }));

  it('should navigate to admin dashboard', fakeAsync(() => {
    component.toAdminDashboard();
    expect(router.navigate).toHaveBeenCalledWith(["/admin"]);
  }));
});
*/