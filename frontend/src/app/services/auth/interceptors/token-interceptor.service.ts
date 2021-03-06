import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HttpEvent } from '@angular/common/http';
import { Injector } from '@angular/core';
import { JwtTokenService } from '../jwt-token.service';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {
  constructor(
    private inj: Injector,
    private jwtTokenService: JwtTokenService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.jwtTokenService.getToken();
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token || ''}`,
        },
      });
    return next.handle(req);
  }
}
