import { Injectable } from '@angular/core';
import { NgModelGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import Model from 'src/app/shared/models/Model';

@Injectable({
  providedIn: 'root',
})
export abstract class BaseService {
  constructor() {}

  abstract createEmpty(): Model;

  abstract getAll(): Observable<Model[]>;

  abstract add(object: Model): Observable<Model[]>;

  abstract update(id: number, object: Model): Observable<Model[]>;

  abstract delete(id: number): Observable<Model[]>;
}
