import {
  MessageService,
  SnackbarColors,
} from 'src/app/services/message/message.service';
import { Category } from './../../../../../../shared/models/Category';
import { CategoryService } from './../../../../../../services/category/category.service';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { SubcategoryService } from 'src/app/services/subcategory/subcategory.service';
import AddDialog from 'src/app/shared/dialog/AddDialog';
import { Subcategory } from 'src/app/shared/models/Subcategory';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-subcategory-dialog',
  templateUrl: './add-subcategory-dialog.component.html',
  styleUrls: ['./add-subcategory-dialog.component.scss'],
})
export class AddSubcategoryDialogComponent
  extends AddDialog<AddSubcategoryDialogComponent>
  implements OnInit {
  newObj: Subcategory;
  form: FormGroup;
  categories: Category[] = [];
  filteredCategories: Observable<Category[]>;
  constructor(
    private readonly fb: FormBuilder,
    public dialogRef: MatDialogRef<AddSubcategoryDialogComponent>,
    public service: SubcategoryService,
    public snackbar: MatSnackBar,
    public catService: CategoryService,
    public messageService: MessageService
  ) {
    super(dialogRef, service, snackbar, messageService);
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      category: [
        '',
        [Validators.required, this.requireMatchCategory.bind(this)],
      ],
      icon: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {
    this.catService.getAll().subscribe((res: Category[]) => {
      res.map((r) => {
        this.categories.push(r);
        this.filteredCategories = this.f.category.valueChanges.pipe(
          startWith(''),
          map((cat) => (cat && typeof cat === 'object' ? cat.name : cat)),
          map((name) => (name ? this.filter(name) : this.categories.slice()))
        );
      });
    });
  }

  onFileSelected(files: FileList): void {
    this.form.patchValue({icon: files.length === 1 ? files : ''});
  }

  onSubmit() {
    this.newObj.name = this.form.value['name'];
    this.newObj.categoryId = this.form.value['category'].id;
    const icon: FileList = this.form.value['icon'];
    
    this.service
      .addMultipart(this.newObj, icon)
      .subscribe((data) => {
        if (!data) {
          this.messageService.openSnackBar(
            this.snackbar,
            'The name of the category should be unique!',
            'End',
            5000,
            SnackbarColors.ERROR
          );
          return;
        }
        this.onSubscriptionCallBack.emit(data);
        this.dialogRef.close();
      }, (error) => {
        this.dialogRef.close();
        console.log(error);
      });
  }

  get f() {
    return this.form.controls;
  }

  displayFn(cat: Category): string {
    return cat.name;
  }

  private requireMatchCategory(control: FormControl) {
    const selection: any = control.value;
    if (this.categories && this.categories.indexOf(selection) < 0) {
      return { requireMatchRegion: true };
    }
    return null;
  }

  private filter(value: string): Category[] {
    const filterValue = value.toLowerCase();
    return this.categories.filter((option) =>
      option.name.toLowerCase().includes(filterValue)
    );
  }
}
