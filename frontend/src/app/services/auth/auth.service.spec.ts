import { HttpClient } from '@angular/common/http';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { Role } from 'src/app/shared/models/Role';
import { UserTokenState } from 'src/app/shared/models/UserTokenState';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    httpClient = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('login success - user token state', fakeAsync(() => {
    const username = 'test@gmail.com';
    const password = '12345';
    let userTokenState: UserTokenState;
    const mockUserTokenState = {
      id: 1,
      accessToken: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'USER',
    };

    service.login(username, password).subscribe((data) => {
      if (data === true)
        userTokenState = JSON.parse(localStorage['currentUser']);
      else fail();
    });

    const req = httpMock.expectOne('http://localhost:9001/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockUserTokenState);

    tick(2000);

    expect(userTokenState.id).toEqual(1);
    expect(userTokenState.token).toEqual('asdfghjk');
    expect(userTokenState.expiresIn).toEqual('12345678');
    expect(userTokenState.userRole).toEqual('USER');

    //clean up
    delete localStorage['currentUser'];
  }));

  it('login error - invalid username', fakeAsync(() => {
    const username = 'test@gmail.com';
    const password = '12345';

    service.login(username, password).subscribe(
      (data) => {
        fail();
      },
      (error) => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne('http://localhost:9001/auth/login');
    expect(req.request.method).toBe('POST');

    tick(2000);

    expect(localStorage['currentUser']).toBeFalsy();
  }));

  it('register success', fakeAsync(() => {
    const name = 'test';
    const surname = 'test';
    const username = 'test@gmail.com';
    const password = '12345';

    service.register(name, surname, username, password).subscribe(() => {});

    const req = httpMock.expectOne('http://localhost:9001/auth/register');
    expect(req.request.method).toBe('POST');
  }));

  it('register error - username already exists', fakeAsync(() => {
    const name = 'test';
    const surname = 'test';
    const username = 'iamtaken@gmail.com';
    const password = '12345';

    service.register(name, surname, username, password).subscribe(
      () => {
        fail();
      },
      (error) => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne('http://localhost:9001/auth/register');
    expect(req.request.method).toBe('POST');
  }));

  it('change password success', fakeAsync(() => {
    const oldPassword = '12345';
    const newPassword = '54321';

    service.changePassword(oldPassword, newPassword).subscribe(() => {});

    const req = httpMock.expectOne(
      'http://localhost:9001/auth/change-password'
    );
    expect(req.request.method).toBe('POST');
  }));

  it('change password error - invalid old password', fakeAsync(() => {
    const oldPassword = 'HEHEnoMjauMacMac';
    const newPassword = '54321';

    service.changePassword(oldPassword, newPassword).subscribe(
      () => {
        fail();
      },
      (error) => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne(
      'http://localhost:9001/auth/change-password'
    );
    expect(req.request.method).toBe('POST');
  }));

  it('activate account - success', fakeAsync(() => {
    const validKey = 'mojaMalaSlatkaMacka';

    service.activateAccount(validKey).subscribe(() => {});

    const req = httpMock.expectOne(
      'http://localhost:9001/auth/register/mojaMalaSlatkaMacka'
    );
    expect(req.request.method).toBe('GET');
  }));

  it('activate account error - invalid activation key', fakeAsync(() => {
    const invalidKey = 'najsladjaMacaNaSvetu';

    service.activateAccount(invalidKey).subscribe(
      () => {
        fail();
      },
      (error) => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne(
      'http://localhost:9001/auth/register/najsladjaMacaNaSvetu'
    );
    expect(req.request.method).toBe('GET');
  }));

  it('forgot password - success', fakeAsync(() => {
    const username = 'test@gmail.com';

    service.forgotPassword(username).subscribe(() => {});

    const req = httpMock.expectOne(
      'http://localhost:9001/auth/forgot-password'
    );
    expect(req.request.method).toBe('POST');
  }));

  it('reset password - success', fakeAsync(() => {
    const newPassword = 'newPass';
    const resetKey = 'josJednaMalaMaca';
    let userTokenState: UserTokenState;
    const mockUserTokenState = {
      id: 1,
      accessToken: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'USER',
    };

    service.resetPassword(newPassword, resetKey).subscribe(() => {
      userTokenState = JSON.parse(localStorage['currentUser']);
    });

    const req = httpMock.expectOne('http://localhost:9001/auth/reset-password');
    expect(req.request.method).toBe('POST');
    req.flush(mockUserTokenState);

    tick(2000);

    expect(userTokenState.id).toEqual(1);
    expect(userTokenState.token).toEqual('asdfghjk');
    expect(userTokenState.expiresIn).toEqual('12345678');
    expect(userTokenState.userRole).toEqual('USER');

    //clean up
    delete localStorage['currentUser'];
  }));

  it('reset password error - invalid key', fakeAsync(() => {
    const newPassword = 'newPassword';
    const invalidKey = 'noKey';

    service.resetPassword(newPassword, invalidKey).subscribe(
      () => {
        fail();
      },
      (error) => {
        expect(error).toBeTruthy();
      }
    );

    const req = httpMock.expectOne('http://localhost:9001/auth/reset-password');
    expect(req.request.method).toBe('POST');

    tick(2000);

    expect(localStorage['currentUser']).toBeFalsy();
  }));

  it('logout - success', fakeAsync(() => {
    service.logout();
    expect(localStorage['currentUser']).toBeFalsy();
  }));

  it('get current user - success', fakeAsync(() => {
    let role: Role;
    let mockUser = Role.USER;
    let noAuth = Role.NO_AUTH;
    const mockUserTokenState = {
      id: 1,
      accessToken: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'USER',
    };
    localStorage['currentUser'] = JSON.stringify(mockUserTokenState);

    role = service.getCurrentUserRole();

    expect(role).toEqual(mockUser);

    delete localStorage['currentUser'];

    role = service.getCurrentUserRole();
    expect(role).toEqual(noAuth);
  }));

  it('check if admin - success', fakeAsync(() => {
    const mockAdminTokenState = {
      id: 1,
      accessToken: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'ADMIN',
    };
    let isAdmin: boolean;

    localStorage['currentUser'] = JSON.stringify(mockAdminTokenState);
    isAdmin = service.checkIfAdmin();

    expect(isAdmin).toBeTruthy();

    delete localStorage['currentUser'];
    isAdmin = service.checkIfAdmin();

    expect(isAdmin).toBeFalsy();
  }));
});
