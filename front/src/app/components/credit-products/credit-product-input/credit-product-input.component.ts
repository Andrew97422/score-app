import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AutoloanService } from 'src/app/shared/services/autoloan.service';

@Component({
  selector: 'credit-product-input',
  templateUrl: './credit-product-input.component.html',
  styleUrls: ['./credit-product-input.component.css']
})
export class CreditProductInputComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private autoloanService: AutoloanService,
    private dialogRef: MatDialogRef<CreditProductInputComponent>) {
      this.form = this.fb.group({
        name: null,
        minAmount: 50000,
        maxAmount: null,
        minTerm: 1,
        maxTerm: 30,
        minRate: 8.4,
        term: null,
        url: null,
        comment: null,
        startDate: new Date(),
        finishDate: null,
        active: null
      });
  }


  submit(): void {
    this.autoloanService.register(this.form.getRawValue());
    this.dialogRef.close();
  }
}
