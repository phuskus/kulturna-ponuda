import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseService<T> {
  constructor() {}

  abstract getAll(): Observable<T[]>;

  abstract add(object: T): Observable<T[]>;

  abstract update(id: number, object: T): Observable<T[]>;

  abstract delete(id: number): Observable<T[]>;
}
