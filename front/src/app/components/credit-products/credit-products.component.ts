import { Component } from '@angular/core';
import { CreditProductService } from '../../shared/services/credit-products.service'
import { MatDialog } from '@angular/material/dialog';
import { CreditProductInputComponent } from './credit-product-input/credit-product-input.component';

@Component({
  selector: 'credit-products',
  templateUrl: './credit-products.component.html',
  styleUrls: ['./credit-products.component.css']
})
export class CreditProductsComponent {
  constructor(
    private dialog: MatDialog,
    private creditProductService: CreditProductService) {

  }

  async createProduct(): Promise<void> {
    await this.dialog.open(CreditProductInputComponent).afterClosed().toPromise();
  }
}
