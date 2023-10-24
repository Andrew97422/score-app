import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { CountActiveLoans } from 'src/app/shared/models/count-active-loans';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { LoginComponent } from '../login/login.component';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent {
  LendingTypeExt = LendingTypeExt;
  
  form: FormGroup;
  maxYearCount = 5;
  
  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private dataService: DataService) {
    this.form = this.fb.group({
      lendingType: { value: LendingType.AUTO_LOAN, disabled: true } ,
      amount: [5350000, Validators.required],
      yearCount: 1,
      monthlyPaymentAmount: null,
      psbClient: null
    });

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
    this.dataService.data = {
      creditAmount: this.form.controls.amount.value,
      lendingType: this.form.controls.lendingType.value,
      amountLoanPayments: this.form.controls.monthlyPaymentAmount.value,
      psbClient: true,
      countActiveLoans: CountActiveLoans.NO_CREDITS
    };

    await this.dialog.open(LoginComponent).afterClosed().toPromise();
  }
  
  private calcMonthlyPaymentAmount(): void {
    const s = this.form.controls.amount.value ?? 0;
    const n = this.form.controls.yearCount.value * 12;
    const p = 8.4 / (12 * 100);
    const x = s * (p + p / ((1+p)**n - 1));
    this.form.controls.monthlyPaymentAmount.setValue(Math.round(x));
  }
}
