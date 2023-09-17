import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RequestInputComponent } from '../my-requests/request-input/request-input.component';
import { MatDialog } from '@angular/material/dialog';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { LendingType } from 'src/app/shared/models/lending-type';

@Component({
  selector: 'shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog) {
    this.form = this.fb.group({
      amount: [null, Validators.required]
    });
  }

  async calcCredit(): Promise<void> {
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Новая заявка',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create,
      data: { creditAmount: this.form.controls.amount.value, lendingType: LendingType.CONSUMER }
    })}).afterClosed().toPromise();
  }
}
