import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';

import { RegisterRoutingModule } from './register-routing.module';
import { RegisterPageComponent } from './pages/register-page/register-page.component';


@NgModule({
  declarations: [RegisterPageComponent],
  imports: [
    CommonModule,
    RegisterRoutingModule,

    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule
  ]
})
export class RegisterModule { }
