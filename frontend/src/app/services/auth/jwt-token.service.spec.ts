import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, TestBed } from '@angular/core/testing';

import { JwtTokenService } from './jwt-token.service';

describe('JwtTokenService', () => {
  let service: JwtTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JwtTokenService],
    });
    service = TestBed.inject(JwtTokenService);
    
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('get token - should return token', fakeAsync(() => {
    let token : string;
    const mockUserTokenState = {
      id: 1,
      token: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'USER'
    };
    localStorage['currentUser'] = JSON.stringify(mockUserTokenState);
    token = service.getToken();

    expect(token).toEqual(mockUserTokenState.token);

    delete localStorage['currentUser'];
    
    token = service.getToken();
    expect(token).toBeFalsy();

  }))

  it('get expiration date - should return date', fakeAsync(() => {
    let expDate : string;
    const mockUserTokenState = {
      id: 1,
      token: 'asdfghjk',
      expiresIn: '12345678',
      userRole: 'USER'
    };
    localStorage['currentUser'] = JSON.stringify(mockUserTokenState);
    expDate = service.getExpirationDate();

    expect(expDate).toEqual(mockUserTokenState.expiresIn);

    delete localStorage['currentUser'];
    
    expDate = service.getExpirationDate();
    expect(expDate).toBeFalsy();

  }))

});
