import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CreditProductService } from 'src/app/shared/services/credit-products.service';

@Component({
  selector: 'credit-product-input',
  templateUrl: './credit-product-input.component.html',
  styleUrls: ['./credit-product-input.component.css']
})
export class CreditProductInputComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private creditProductService: CreditProductService,
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
    this.creditProductService.register(this.form.getRawValue());
    this.dialogRef.close();
  }
}
