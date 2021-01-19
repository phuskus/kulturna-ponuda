import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessageService } from 'src/app/services/message/message.service';
import { FormValidationService } from 'src/app/services/validation/form-validation.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  resetForm: FormGroup;
  hide: boolean = true;

  constructor(private readonly fb: FormBuilder, 
    private router: Router, 
    private authService: AuthService, 
    private formValidationService: FormValidationService,
    private snackBar: MatSnackBar,
    private messageService : MessageService,
    private activatedRoute: ActivatedRoute) { 
      this.resetForm = this.fb.group({
        newPassword: ['', [Validators.required, Validators.minLength(5)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(5)]]
      });
    }

  ngOnInit(): void {
    if(!this.activatedRoute.snapshot.params.key)
      this.router.navigate(['/']);
  }

  get f() {
    return this.resetForm.controls;
  }

  onSubmit() {
    if (this.resetForm.invalid) {
      this.formValidationService.validateAllFormFields(this.resetForm);
      return;
    }
    let newPassword = this.resetForm.value['newPassword'];
    let resetKey = this.activatedRoute.snapshot.params.key;
    this.authService.resetPassword(newPassword, resetKey).subscribe(() => {
      this.messageService.openSnackBar(this.snackBar, 'Successfully reset password!', 'End', 6000);
      this.router.navigate(['/']);
    }, error => {
      console.log(error);
    })
  }

}
