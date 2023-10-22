import { Component } from '@angular/core';
import { RegisterService } from 'src/app/shared/services/register-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PsbAuthorizationComponent } from '../psb-authorization/psb-authorization.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthorizationSource } from 'src/app/shared/models/authorization-source';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private dataService: DataService,
    private registerService: RegisterService,
    private dialogRef: MatDialogRef<LoginComponent>) {
      dataService.dialogRef = dialogRef;
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
    this.registerService.login(this.form.getRawValue(), AuthorizationSource.None);
  }

  async psbAuthorize(): Promise<void> {
    await this.dialog.open(PsbAuthorizationComponent).afterClosed().toPromise();
  }
}
