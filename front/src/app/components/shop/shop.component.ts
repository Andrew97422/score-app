import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from 'src/app/shared/services/register-service';

@Component({
  selector: 'shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService) {
    this.form = this.fb.group({
      name: null,
      amount: [null, Validators.required]
    });
  }

  calcCredit(): void {
    this.registerService.sendRequest(this.form.getRawValue());
  }
}
