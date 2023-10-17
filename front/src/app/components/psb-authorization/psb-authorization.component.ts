import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthorizationSource } from 'src/app/shared/models/authorization-source';
import { RegisterService } from 'src/app/shared/services/register-service';
import { RequestData } from 'src/app/shared/models/request-data';
import { DataService } from 'src/app/shared/services/data.service';
import { CountActiveLoans } from 'src/app/shared/models/count-active-loans';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { RequestInputComponent } from '../my-requests/request-input/request-input.component';

@Component({
  selector: 'psb-authorization',
  templateUrl: './psb-authorization.component.html',
  styleUrls: ['./psb-authorization.component.css']
})
export class PsbAuthorizationComponent {
  form: FormGroup;

  constructor(
    private dialog: MatDialog,
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
    this.registerService.login(this.form.getRawValue(), AuthorizationSource.PSB, !this.data);

    if (!this.data) return;

    const creditCount = this.dataService.getCredits(this.form.controls.username.value)?.length;
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Новая заявка',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create,
      data: {
        creditAmount: this.data.amount,
        lendingType: this.data.lendingType,
        amountLoanPayments: this.data.currentDebtLoad,
        psbClient: true,
        countActiveLoans: (creditCount > 0 && creditCount <= 2) ? CountActiveLoans.FROM_ONE_TO_TWO 
        : (creditCount > 2 && creditCount <= 5) ? CountActiveLoans.FROM_THREE_TO_FIVE
        : (creditCount > 5) ? CountActiveLoans.MORE_THAN_FIVE
        : CountActiveLoans.NO_CREDITS
      }
    })}).afterClosed().toPromise();
  }
}
