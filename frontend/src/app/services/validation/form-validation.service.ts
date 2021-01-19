import { Injectable } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormValidationService {

  constructor() { }

  validateAllFormFields(formGroup : FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    })
  }

  clearFormAndValidators(formGroup : FormGroup) {
    formGroup.reset();
    Object.keys(formGroup.controls).forEach(field => {
      let control = formGroup.get(field);
      control.clearValidators();
      control.updateValueAndValidity();
    })
  }

  onTouchField(f : { [key: string]: AbstractControl; }, field: string, fieldErr: boolean) : boolean {
    if (fieldErr) {
      if (f[field].touched) {
        return false;
      }
    }
  }
}
