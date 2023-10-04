import { Component } from '@angular/core';
import { StateService } from '../../../../shared/services/state-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { RegisterService } from 'src/app/shared/services/register-service';
import { RequestData } from 'src/app/shared/models/request-data';
import { MatDialog } from '@angular/material/dialog';
import { PsbAuthorizationComponent } from 'src/app/components/psb-authorization/psb-authorization.component';

@Component({
  selector: 'product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  LendingTypeExt = LendingTypeExt;
  
  form: FormGroup;
  maxYearCount = 5;
  
  constructor(
    public stateService: StateService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private registerService: RegisterService) {
    this.form = this.fb.group({
      lendingType: stateService.product?.lendingType,
      amount: [stateService.product?.price, Validators.required],
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
            currentDebtLoad: this.form.controls.monthlyPaymentAmount.value
          })
      });

      return;
    }

    this.registerService.sendRequest(new RequestData({
      amount: this.form.controls.amount.value,
      lendingType: this.form.controls.lendingType.value,
      psbClient: this.form.controls.psbClient.value,
      currentDebtLoad: this.form.controls.monthlyPaymentAmount.value
    }));
  }
  
  private calcMonthlyPaymentAmount(): void {
    const s = this.form.controls.amount.value ?? 0;
    const n = this.form.controls.yearCount.value * 12;
    const p = 8.4 / (12 * 100);
    const x = s * (p + p / ((1+p)**n - 1));
    this.form.controls.monthlyPaymentAmount.setValue(Math.round(x));
  }
}
