import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import * as moment from 'moment';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { AutoloanService } from 'src/app/shared/services/autoloan.service';
import { ConsumerService } from 'src/app/shared/services/consumer-service';
import { MortgageService } from 'src/app/shared/services/mortgage.service';

@Component({
  selector: 'credit-product-input',
  templateUrl: './credit-product-input.component.html',
  styleUrls: ['./credit-product-input.component.css']
})
export class CreditProductInputComponent {
  LendingTypeExt = LendingTypeExt;
  
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private consumerService: ConsumerService,
    private autoloanService: AutoloanService,
    private mortgageService: MortgageService,
    private dialogRef: MatDialogRef<CreditProductInputComponent>,
    @Inject(MAT_DIALOG_DATA) public data: InputDialogModel<any>) {
      this.form = this.fb.group({
        id: null,
        lendingType: LendingType.CONSUMER,
        name: null,
        minAmount: 10000,
        maxAmount: 5000000,
        minTerm: 1,
        maxTerm: 30,
        minRate: 8.4,
        term: null,
        url: null,
        comment: null,
        startDate: new Date(),
        finishDate: new Date()
      });

      if (data.data) {
        this.form.controls.id.setValue(data.data.id);
        this.form.controls.lendingType.setValue(data.data.lendingType);
        this.form.controls.name.setValue(data.data.name);
        this.form.controls.minAmount.setValue(data.data.minAmount);
        this.form.controls.maxAmount.setValue(data.data.maxAmount);
        this.form.controls.maxTerm.setValue(data.data.maxTerm);
        this.form.controls.minTerm.setValue(data.data.minTerm);
        this.form.controls.minRate.setValue(data.data.minRate);
        this.form.controls.term.setValue(data.data.term);
        this.form.controls.url.setValue(data.data.url);
        this.form.controls.comment.setValue(data.data.comment);
        this.form.controls.startDate.setValue(new Date(data.data.startDate));
        this.form.controls.finishDate.setValue(new Date(data.data.finishDate));

        this.form.controls.lendingType.disable();
      }
  }

  submit(): void {
    const lendingType = this.form.controls.lendingType.value;
    const result = this.form.getRawValue();
    result.startDate = moment(result.startDate).format('yyyy-MM-DDTHH:mm:ss');
    result.finishDate = !result.finishDate ? null: moment(result.finishDate).format('yyyy-MM-DDTHH:mm:ss');

    if (this.data.dialogType == InputDialogType.Create) {
      if (lendingType == LendingType.AUTO_LOAN) {
        this.autoloanService.register(result);
      }
      
      if (lendingType == LendingType.CONSUMER) {
        this.consumerService.register(result);
      }
  
      if (lendingType == LendingType.MORTGAGE) {
        this.mortgageService.register(result);
      }
    }

    if (this.data.dialogType == InputDialogType.Edit) {
      if (lendingType == LendingType.AUTO_LOAN) {
        this.autoloanService.edit(result);
      }
      
      if (lendingType == LendingType.CONSUMER) {
        this.consumerService.edit(result);
      }
  
      if (lendingType == LendingType.MORTGAGE) {
        this.mortgageService.edit(result);
      }
    }

    this.dialogRef.close();
  }
}
