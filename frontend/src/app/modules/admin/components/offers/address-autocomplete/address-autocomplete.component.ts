import { HttpClient } from '@angular/common/http';
import { Input } from '@angular/core';
import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatOptionSelectionChange } from '@angular/material/core';
import { Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-address-autocomplete',
  templateUrl: './address-autocomplete.component.html',
  styleUrls: ['./address-autocomplete.component.scss'],
})
export class AddressAutocompleteComponent implements OnDestroy, OnInit {
  @Output()
  locationChange: EventEmitter<PlaceSuggestion> = new EventEmitter<PlaceSuggestion>();
  @Input() address: string;
  searchOptions: Subject<PlaceSuggestion[]> = new Subject<PlaceSuggestion[]>();
  inputFieldFormControl: FormControl = new FormControl('', [
    Validators.required,
  ]);
  test: string = 'Jevrejska 8, Novi Sad';
  private valueChangesSub: Subscription;
  private choosenOption: PlaceSuggestion;

  private userInputTimeout: number;
  private requestSub: Subscription;

  constructor(private http: HttpClient) {
    this.valueChangesSub = this.inputFieldFormControl.valueChanges.subscribe(
      (value) => {
        if (this.userInputTimeout) {
          window.clearTimeout(this.userInputTimeout);
        }

        if (this.choosenOption && this.choosenOption.shortAddress === value) {
          this.searchOptions.next(null);
          return;
        }

        if (!value || value.length < 3) {
          this.searchOptions.next(null);
          return;
        }

        this.userInputTimeout = window.setTimeout(() => {
          this.generateSuggestions(value);
        }, 500);
      }
    );
  }

  ngOnInit() {
    this.inputFieldFormControl.setValue(this.address);
  }

  ngOnDestroy() {
    this.valueChangesSub.unsubscribe();
  }

  private generateSuggestions(text: string) {
    const url = `https://api.geoapify.com/v1/geocode/autocomplete?text=${text}&limit=5&apiKey=3e882b80c3fc49a88d287f7a8ab80964`;

    if (this.requestSub) {
      this.requestSub.unsubscribe();
    }

    this.requestSub = this.http.get(url).subscribe(
      (data: GeoJSON.FeatureCollection) => {
        const placeSuggestions = data.features.map((feature) => {
          const properties: GeocodingFeatureProperties = feature.properties as GeocodingFeatureProperties;

          return {
            shortAddress: this.generateShortAddress(properties),
            fullAddress: this.generateFullAddress(properties),
            data: properties,
          };
        });

        this.searchOptions.next(
          placeSuggestions.length ? placeSuggestions : null
        );
      },
      (err) => {
        console.log(err);
      }
    );
  }

  private generateShortAddress(properties: GeocodingFeatureProperties): string {
    let shortAddress = properties.name;

    if (!shortAddress) {
      // name is not set for buildings
      shortAddress = `${properties.street} ${properties.housenumber}`;
    }

    shortAddress +=
      properties.city && properties.city !== properties.name
        ? `, ${properties.city}`
        : '';
    shortAddress +=
      properties.country && properties.country !== properties.name
        ? `, ${properties.country}`
        : '';

    return shortAddress;
  }

  private generateFullAddress(properties: GeocodingFeatureProperties): string {
    let fullAddress = properties.name;
    fullAddress += properties.street ? `, ${properties.street}` : '';
    fullAddress += properties.housenumber ? ` ${properties.housenumber}` : '';
    fullAddress +=
      properties.postcode && properties.city
        ? `, ${properties.postcode} ${properties.city}`
        : '';
    fullAddress +=
      !properties.postcode &&
      properties.city &&
      properties.city !== properties.name
        ? `, ${properties.city}`
        : '';
    fullAddress += properties.state ? `, ${properties.state}` : '';
    fullAddress +=
      properties.country && properties.country !== properties.name
        ? `, ${properties.country}`
        : '';
    return fullAddress;
  }

  public optionSelectionChange(
    option: PlaceSuggestion,
    event: MatOptionSelectionChange
  ) {
    if (event.isUserInput) {
      this.choosenOption = option;
      this.locationChange.emit(option);
    }
  }
}

export interface PlaceSuggestion {
  shortAddress: string;
  fullAddress: string;
  data: GeocodingFeatureProperties;
}

interface GeocodingFeatureProperties {
  name: string;
  country: string;
  state: string;
  postcode: string;
  city: string;
  street: string;
  housenumber: string;
  lat: number;
  long: number;
}
