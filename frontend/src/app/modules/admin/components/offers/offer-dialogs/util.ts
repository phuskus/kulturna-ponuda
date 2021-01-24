import { Injectable } from '@angular/core';
import { FormControl, ValidationErrors } from '@angular/forms';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';

@Injectable({
    providedIn: 'root'
})
export class UtilOffer {

    constructor() {}

    public requireMatch(control: FormControl, elements: string[], field: string):
    ValidationErrors | null {
      const selection: any = control.value;
      if (elements && elements.indexOf(selection) < 0) {
        if (field === "category") {
            return { requireMatchRegion: true }
        } else if (field === "region") {
            return { requireMatchCategory: true }
        }
      }
      return null; 
    }

    public filter(value: string, options: string[]) {
        const filterValue = value.toLowerCase();
        return options.filter(option => 
        option.toLowerCase().includes(filterValue));
    }

    public addressAutocompleteChanged($event, offer: CulturalOffer) {
        offer.address = $event.data.housenumber ? $event.data.street + ' ' + $event.data.housenumber : $event.data.street;
        offer.latitude = $event.data.lat;
        offer.longitude = $event.data.lon;
        offer.city = $event.data.city;
    }

}