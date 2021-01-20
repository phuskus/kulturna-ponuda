import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { ResetPasswordRoutingModule } from './reset-password-routing.module';
import { ValidateEqualModule } from 'ng-validate-equal';


@NgModule({
  declarations: [ResetPasswordComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSnackBarModule,
    ResetPasswordRoutingModule,
    ValidateEqualModule
  ]
})
export class ResetPasswordModule { }
