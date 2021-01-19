import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgModelGroup } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Model from 'src/app/shared/models/Model';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseService {
  httpOptions = {
    headers: new HttpHeaders({
      Authentication: '',
      'Content-Type': 'application/json',
      'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImNvdmlkMTkuY2xpbmljLmxsY0BnbWFpbC5jb20iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTEwNDUyMTksImV4cCI6MTYxMTA4ODQxOX0.rOtKRjBZ4nVKhEB9NV8mEa5D_PgYds_mvt6br46qD0Ck8kRcoY-MIBwtHceYPAsRhZMFoqbTs4goyhxG5mRecA`
    }),
  };

  constructor(public url: string, public http: HttpClient) {}

  abstract createEmpty(): Model;

  getAll(): Observable<Model[]> {
    return this.http.get<Model[]>(this.url, this.httpOptions);
  }

  get(id: number): Observable<Model> {
    return this.http.get<Model>(this.url + '/' + id, this.httpOptions);
  }

  add(object: Model): Observable<Model> {
    return this.http
      .post<Model>(this.url, object, this.httpOptions)
      .pipe(catchError(this.handleError<Model>()));
  }

  update(id: number, object: Model): Observable<Model> {
    return this.http.put<Model>(this.url + '/' + id, object, this.httpOptions);
  }

  delete(id: number): Observable<Model> {
    return this.http.delete<Model>(this.url + '/' + id, this.httpOptions);
  }

  handleError<P>(result?: P) {
    return (error: any): Observable<P> => {
      console.error(error);
      return of(result as P);
    };
  }
}
