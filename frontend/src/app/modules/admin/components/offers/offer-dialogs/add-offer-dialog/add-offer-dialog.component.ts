import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { OfferService } from 'src/app/services/offer/offer.service';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { CulturalOffer } from 'src/app/shared/models/CulturalOffer';
import { Subcategory } from 'src/app/shared/models/Subcategory';

@Component({
  selector: 'app-add-offer-dialog',
  templateUrl: './add-offer-dialog.component.html',
  styleUrls: ['./add-offer-dialog.component.scss']
})
export class AddOfferDialogComponent extends AddDialog<AddOfferDialogComponent> 
 {
  
  newObj: CulturalOffer;
  cultForm: FormGroup;
  filesSelected: FileList;
  regions: string[] = ['Vojvodina', 'Central Serbia', 'Eastern Serbia', 'Western Serbia', 'Southern Serbia'];
  categories: string[] = [];
  filteredRegions: Observable<string[]>;
  filteredSubcategories: Observable<string[]>;

  constructor(
    private readonly fb: FormBuilder,
    private router: Router,
    public dialogRef: MatDialogRef<AddOfferDialogComponent>,
    public service: OfferService,
    public subcatService: SubcategoryService
  ) {
    super(dialogRef, service);
    this.cultForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      category: ['', [Validators.required, this.requireMatchCategory.bind(this)]],
      region: ['', [Validators.required, this.requireMatchRegion.bind(this)]]
    })
   }

  
   get f() {
     return this.cultForm.controls;
   }

  ngOnInit() {
    this.subcatService.getAll().subscribe((res: Subcategory[]) => {
      res.map(r => {
        this.categories.push(r.name);
        this.filteredSubcategories = this.f.category.valueChanges
          .pipe(startWith(''), map(value => this.filter(value, this.categories)));
      })
    });
    this.filteredRegions = this.f.region.valueChanges
      .pipe(startWith(''), map(value => this.filter(value, this.regions)));
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
      alert('Please check address again!');
      return;
    }

    this.service.add(this.newObj).subscribe(data => this.onSubscriptionCallBack.emit(data));
    this.dialogRef.close();
  }

  autocompleteChanged($event: any) {
    this.newObj.address = $event.data.housenumber ? $event.data.street + ' ' + $event.data.housenumber : $event.data.street;
    this.newObj.latitude = $event.data.lat;
    this.newObj.longitude = $event.data.lon;
    this.newObj.city = $event.data.city;
  }

  private requireMatchRegion(control: FormControl):
    ValidationErrors | null {
      const selection: any = control.value;
      if (this.regions && this.regions.indexOf(selection) < 0) {
        return { requireMatchRegion: true };
      }
      return null; 
  }

  private requireMatchCategory(control: FormControl):
    ValidationErrors | null {
      const selection: any = control.value;
      if (this.categories && this.categories.indexOf(selection) < 0) {
        return { requireMatchCategory: true };
      }
      return null; 
  }

    
    private filter(value: string, options: string[]) {
      const filterValue = value.toLowerCase();
      return options.filter(option => 
        option.toLowerCase().includes(filterValue));
    }

}
