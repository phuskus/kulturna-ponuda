import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgModelGroup } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AppSettings } from 'src/app/app-settings/AppSettings';
import Model from 'src/app/shared/models/Model';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  public url: string;

  constructor(public path: string, public http: HttpClient) {
    this.url = AppSettings.API_ENDPOINT + path;
  }

  abstract createEmpty(): Model;

  getAll(): Observable<Model[]> {
    return this.http
      .get<Model[]>(this.url, this.httpOptions)
      .pipe(catchError(this.handleError<Model[]>()));
  }

  get(id: number): Observable<Model> {
    return this.http
      .get<Model>(this.url + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError<Model>()));
  }

  add(object: Model): Observable<Model> {
    return this.http
      .post<Model>(this.url, object, this.httpOptions)
      .pipe(catchError(this.handleError<Model>()));
  }

  addMultipart(object: Model, files: FileList): Observable<Model> {
    throw Error('Not implemented');
  }

  update(id: number, object: Model): Observable<Model> {
    return this.http
      .put<Model>(this.url + '/' + id, object, this.httpOptions)
      .pipe(catchError(this.handleError<Model>()));
  }

  delete(id: number): Observable<Model> {
    return this.http
      .delete<Model>(this.url + '/' + id, this.httpOptions)
      .pipe(catchError(this.handleError<Model>()));
  }

  handleError<P>(result?: P) {
    return (error: any): Observable<P> => {
      console.error(error);
      return of(result as P);
    };
  }
}
