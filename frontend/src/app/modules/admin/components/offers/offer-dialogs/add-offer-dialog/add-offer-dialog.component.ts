import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MessageService } from 'src/app/services/message/message.service';
import { OfferService } from 'src/app/services/offer/offer.service';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { UtilOffer } from '../Util';

@Component({
  selector: 'app-add-offer-dialog',
  templateUrl: './add-offer-dialog.component.html',
  styleUrls: ['./add-offer-dialog.component.scss'],
})
export class AddOfferDialogComponent extends AddDialog<AddOfferDialogComponent> {
  newObj: CulturalOffer;
  cultForm: FormGroup;
  filesSelected: FileList;
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

  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<AddOfferDialogComponent>,
    public service: OfferService,
    public subcatService: SubcategoryService,
    public snackbar: MatSnackBar,
    public messageService: MessageService,
    public formValidationService: FormValidationService,
    public utilOffer: UtilOffer
  ) {
    super(dialogRef, service);
    this.cultForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      category: [
        '',
        [Validators.required, this.requireMatchCategory.bind(this)],
      ],
      region: ['', [Validators.required, this.requireMatchRegion.bind(this)]],
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
    });
    this.filteredRegions = this.f.region.valueChanges.pipe(
      startWith(''),
      map((value) => this.utilOffer.filter(value, this.regions))
    );
  }

  get f() {
    return this.cultForm.controls;
  }

  onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onSubmit() {
    let admin = JSON.parse(localStorage['currentUser']);
    this.newObj.admin = admin.id;
    this.newObj.name = this.cultForm.value['name'];
    this.newObj.description = this.cultForm.value['description'];
    this.newObj.categoryName = this.cultForm.value['category'];
    this.newObj.region = this.cultForm.value['region'];

    if (!this.newObj.latitude || !this.newObj.longitude) {
      this.messageService.openSnackBar(
        this.snackbar,
        'Please check the address!',
        'End',
        5000
      );
      return;
    }

    this.service
      .addMultipart(this.newObj, this.filesSelected)
      .subscribe((data) => {
        if (!data) {
          this.messageService.openSnackBar(
            this.snackbar,
            'The name of the cultural offer should be unique!',
            'End',
            5000
          );
          return;
        }
        this.onSubscriptionCallBack.emit(data);
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
}
