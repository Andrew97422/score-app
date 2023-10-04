import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthorizationSource } from 'src/app/shared/models/authorization-source';
import { RegisterService } from 'src/app/shared/services/register-service';
import { RequestData } from 'src/app/shared/models/request-data';
import { DataService } from 'src/app/shared/services/data.service';
import { CountActiveLoans } from 'src/app/shared/models/count-active-loans';

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
    private dataService: DataService,
    private dialogRef: MatDialogRef<PsbAuthorizationComponent>,
    @Inject(MAT_DIALOG_DATA) private data: RequestData) {
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
    this.dialogRef.close();
    this.registerService.login(this.form.getRawValue(), AuthorizationSource.PSB);

    if (!this.data) return;
    const creditCount = this.dataService.getCredits(this.form.controls.username.value)?.length;
    const requestData = new RequestData({
      amount: this.data.amount,
      lendingType: this.data.lendingType,
      currentDebtLoad: this.data.currentDebtLoad,
      psbClient: true,
      countActiveLoans: (creditCount > 0 && creditCount <= 2) ? CountActiveLoans.FROM_ONE_TO_TWO 
      : (creditCount > 2 && creditCount <= 5) ? CountActiveLoans.FROM_THREE_TO_FIVE
      : (creditCount > 5) ? CountActiveLoans.MORE_THAN_FIVE
      : CountActiveLoans.NO_CREDITS
    });

    this.registerService.sendRequest(requestData);
  }
}
