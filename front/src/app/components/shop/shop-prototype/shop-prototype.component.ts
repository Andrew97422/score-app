import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { RequestInputComponent } from '../../my-requests/request-input/request-input.component';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { CountActiveLoans } from 'src/app/shared/models/count-active-loans';
import { PsbAuthorizationComponent } from '../../psb-authorization/psb-authorization.component';
import { RequestData } from 'src/app/shared/models/request-data';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'shop-prototype',
  templateUrl: './shop-prototype.component.html',
  styleUrls: ['./shop-prototype.component.css']
})
export class ShopPrototypeComponent {
  LendingTypeExt = LendingTypeExt;
  
  form: FormGroup;
  maxYearCount = 5;
  
  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog) {
    this.form = this.fb.group({
      lendingType: LendingType.MORTGAGE,
      amount: [null, Validators.required],
      yearCount: 1,
      monthlyPaymentAmount: null,
      psbClient: null
    });

    this.form.controls.monthlyPaymentAmount.disable();
    this.form.controls.lendingType.valueChanges.subscribe((type) => {
      if (type == LendingType.MORTGAGE) {
        this.maxYearCount = 30;
      } else {
        this.maxYearCount = 5;
      }
    });

    this.calcMonthlyPaymentAmount();

    this.form.valueChanges.subscribe(() => {
      if (this.form.invalid) return;
      this.calcMonthlyPaymentAmount();
    });
  }

  async calcCredit(): Promise<void> {
    if (this.form.controls.psbClient.value) {
      this.dialog.open(PsbAuthorizationComponent, {
          data: new RequestData({
            amount: this.form.controls.amount.value,
            lendingType: this.form.controls.lendingType.value,
            currentDebtLoad: this.form.controls.monthlyPaymentAmount.value,
          })
      });

      return;
    }

    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Новая заявка',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create,
      data: {
        creditAmount: this.form.controls.amount.value,
        lendingType: this.form.controls.lendingType.value,
        amountLoanPayments: this.form.controls.monthlyPaymentAmount.value,
        psbClient: true,
        countActiveLoans: CountActiveLoans.NO_CREDITS
      }
    })}).afterClosed().toPromise();
  }
  
  private calcMonthlyPaymentAmount(): void {
    const s = this.form.controls.amount.value ?? 0;
    const n = this.form.controls.yearCount.value * 12;
    const p = 8.4 / (12 * 100);
    const x = s * (p + p / ((1+p)**n - 1));
    this.form.controls.monthlyPaymentAmount.setValue(Math.round(x));
  }
}
