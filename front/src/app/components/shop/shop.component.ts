import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RequestInputComponent } from '../my-requests/request-input/request-input.component';
import { MatDialog } from '@angular/material/dialog';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';

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
    private dialog: MatDialog) {
    this.form = this.fb.group({
      lendingType: LendingType.CONSUMER,
      amount: [null, Validators.required],
      yearCount: 1,
      monthlyPaymentAmount: null
    });
    this.form.controls.monthlyPaymentAmount.disable();
    this.form.controls.lendingType.valueChanges.subscribe((type) => {
      if (type == LendingType.MORTGAGE) {
        this.maxYearCount = 30;
      } else {
        this.maxYearCount = 5;
      }
    });

    this.form.valueChanges.subscribe(() => {
      if (this.form.invalid) return;

      const s = this.form.controls.amount.value ?? 0;
      const n = this.form.controls.yearCount.value * 12;
      const p = 8.4 / (12 * 100);
      const x = s * (p + p / ((1+p)**n - 1));
      this.form.controls.monthlyPaymentAmount.setValue(x);
    });
  }

  async calcCredit(): Promise<void> {
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Новая заявка',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create,
      data: { 
        creditAmount: this.form.controls.amount.value,
        lendingType: this.form.controls.lendingType.value
      }
    })}).afterClosed().toPromise();
  }
}
