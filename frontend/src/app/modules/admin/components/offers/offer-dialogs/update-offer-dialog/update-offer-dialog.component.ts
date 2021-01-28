import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import UpdateDialog from 'src/app/shared/dialog/UpdateDialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { UtilOffer } from '../Util';

@Component({
  selector: 'app-update-offer-dialog',
  templateUrl: './update-offer-dialog.component.html',
  styleUrls: ['./update-offer-dialog.component.scss'],
})
export class UpdateOfferDialogComponent extends UpdateDialog<UpdateOfferDialogComponent> {
  newObj: CulturalOffer;
  cultForm: FormGroup;
  regions: string[] = [
    'Vojvodina',
    'Central Serbia',
    'Eastern Serbia',
    'Western Serbia',
    'Southern Serbia',
  ];
  categories: string[] = [];
  filteredRegions: Observable<string[]>;
  filteredSubcategories: Observable<string[]>;
  address: string = '';

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<UpdateOfferDialogComponent>,
    public service: OfferService,
    public subcatService: SubcategoryService,
    @Inject(MAT_DIALOG_DATA) public data: CulturalOffer,
    public utilOffer: UtilOffer,
    public snackbar: MatSnackBar,
    public messageService: MessageService
  ) {
    super(dialogRef, service, snackbar, messageService, data);
    this.cultForm = this.fb.group({
      name: [this.newObj.name, [Validators.required]],
      description: [this.newObj.description, [Validators.required]],
      category: [
        this.newObj.categoryName,
        [this.requireMatchCategory.bind(this)],
      ],
      region: [this.newObj.region, [this.requireMatchRegion.bind(this)]],
    });
  }

  ngOnInit(): void {
    this.subcatService.getAll().subscribe((res: Subcategory[]) => {
      res.map((r) => {
        this.categories.push(r.name);
        this.filteredSubcategories = this.f.category.valueChanges.pipe(
          startWith(''),
          map((value) => this.utilOffer.filter(value, this.categories))
        );
      });
      this.cultForm.controls['category'].updateValueAndValidity();
    });
    this.filteredRegions = this.f.region.valueChanges.pipe(
      startWith(''),
      map((value) => this.utilOffer.filter(value, this.regions))
    );
    this.address = this.newObj.address + ', ' + this.newObj.city;
  }

  get f() {
    return this.cultForm.controls;
  }

  onSubmit() {
    let admin = JSON.parse(localStorage['currentUser']);
    this.newObj.admin = admin.id;
    this.newObj.name = this.cultForm.value['name'];
    this.newObj.description = this.cultForm.value['description'];
    this.newObj.categoryName = this.cultForm.value['category'];
    this.newObj.region = this.cultForm.value['region'];

    if (!this.newObj.latitude || !this.newObj.longitude || !this.valid()) {
      this.snackbarError("Admin, you're smart! Check this again!");
      return;
    }

    this.service.update(this.newObj.id, this.newObj).subscribe((data) => {
      if (!data) {
        this.snackbarError('The name of the cultural offer should be unique!');
        return;
      }
      this.onSubscriptionCallBack.emit(data);
      this.snackbarSuccess('Successfully updated Offer!');
      this.dialogRef.close();
    });
  }

  autocompleteChanged($event: any) {
    this.utilOffer.addressAutocompleteChanged($event, this.newObj);
  }

  private requireMatchRegion(control: FormControl) {
    return this.utilOffer.requireMatch(control, this.regions, 'region');
  }

  private requireMatchCategory(control: FormControl) {
    return this.utilOffer.requireMatch(control, this.categories, 'category');
  }

  private valid() {
    if (
      !this.cultForm.controls['name'].valid ||
      !this.cultForm.controls['description'].valid ||
      !this.cultForm.controls['category'].valid ||
      !this.cultForm.controls['region'].valid
    ) {
      return false;
    }
    return true;
  }
}
