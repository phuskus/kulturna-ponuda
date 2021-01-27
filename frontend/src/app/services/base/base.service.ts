import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
  HttpResponse,
} from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { NgModelGroup } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import Model from 'src/app/shared/models/Model';

@Injectable({
  providedIn: 'root',
})
export class BaseService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  public url: string;

  constructor(public http: HttpClient, @Optional() public path?: string) {
    if (path) this.url = AppSettings.API_ENDPOINT + path;
  }

  createEmpty(): Model {throw new Error("")};

  getAll(): Observable<Model[]> {
    return this.http
      .get<Model[]>(this.url, this.httpOptions)
      .pipe(catchError(this.handleError<Model[]>('getAll')));
  }

  get(id: number): Observable<Model> {
    return this.http
      .get<Model>(this.url + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError<Model>('get')));
  }

  add(object: Model): Observable<Model> {
    return this.http
      .post<Model>(this.url, object, this.httpOptions)
      .pipe(catchError(this.handleError<Model>('add')));
  }

  addMultipart(object: Model, files: FileList): Observable<Model> {
    throw Error('Not implemented');
  }

  update(id: number, object: Model): Observable<Model> {
    return this.http
      .put<Model>(this.url + '/' + id, object, this.httpOptions)
      .pipe(catchError(this.handleError<Model>('update')));
  }

  delete(id: number): Observable<Model> {
    return this.http
      .delete<Model>(this.url + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError<Model>('delete')));
  }

  public handleError<T>(operation = 'operation') {
    return (error: HttpErrorResponse): Observable<T> => {
      console.error(error); // log to console instead

      const message =
        error.error instanceof ErrorEvent
          ? error.error.message
          : `server returned code ${error.status} with body "${error.error}"`;

      throw new Error(`${operation} failed: ${message}`);
    };
  }
}
