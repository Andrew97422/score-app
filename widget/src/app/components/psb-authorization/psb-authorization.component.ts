import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthorizationSource } from 'src/app/shared/models/authorization-source';
import { RegisterService } from 'src/app/shared/services/register-service';

@Component({
  selector: 'psb-authorization',
  templateUrl: './psb-authorization.component.html',
  styleUrls: ['./psb-authorization.component.css']
})
export class PsbAuthorizationComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private dialogRef: MatDialogRef<PsbAuthorizationComponent>) {
      this.form = this.fb.group({
        username: [
          null, [Validators.required]
        ],
        password: [
          null, [Validators.required]
        ]
      });
  }

  async login(): Promise<void> {
    this.registerService.login(this.form.getRawValue(), AuthorizationSource.PSB);
    this.dialogRef.close();
  }
}
