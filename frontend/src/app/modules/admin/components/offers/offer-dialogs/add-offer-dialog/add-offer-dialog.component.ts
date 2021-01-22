import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';
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
  filesSelected: FileList;
  regions: string[] = ['Vojvodina', 'Central Serbia', 'Eastern Serbia', 'Western Serbia', 'Southern Serbia'];
  subcategories: string[] = [];
  categoryName: string;
  region: string = '';

  constructor(
    public dialogRef: MatDialogRef<AddOfferDialogComponent>,
    public service: OfferService,
    public subcatService: SubcategoryService
  ) {
    super(dialogRef, service);
   }

  ngOnInit() {
    this.subcatService.getAll().subscribe((res: Subcategory[]) => {
      res.map(r => {
        this.subcategories.push(r.name);
      })
    })
  }


   onFilesSelected(files: FileList): void {
    this.filesSelected = files;
  }

  onAddCulturalOffer() {

  }

}
