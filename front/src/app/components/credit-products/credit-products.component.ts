import { Component } from '@angular/core';
import { AutoloanService } from '../../shared/services/autoloan.service'
import { MatDialog } from '@angular/material/dialog';
import { CreditProductInputComponent } from './credit-product-input/credit-product-input.component';
import { AutoLoanProduct, CommonProduct, ConsumerProduct } from 'src/app/shared/models/common-product';
import { ConsumerService } from 'src/app/shared/services/consumer-service';
import { MortgageService } from 'src/app/shared/services/mortgage.service';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ConfirmData } from '../confirm-dialog/confirm-data.model';
import { LendingType } from 'src/app/shared/models/lending-type';

@Component({
  selector: 'credit-products',
  templateUrl: './credit-products.component.html',
  styleUrls: ['./credit-products.component.css']
})
export class CreditProductsComponent {
  LendingType = LendingType;

  selectProduct: CommonProduct;
  autoLoanProducts: AutoLoanProduct[];
  consumerProducts: ConsumerProduct[];
  mortgageProducts: AutoLoanProduct[];

  constructor(
    private dialog: MatDialog,
    private consumerService: ConsumerService,
    private autoloanService: AutoloanService,
    private mortgageService: MortgageService) {
      consumerService.getProducts().subscribe(x => {
        this.consumerProducts = x as ConsumerProduct[];
        this.selectProduct = this.consumerProducts[0];
      });

      autoloanService.getProducts().subscribe(x => {
        this.autoLoanProducts = x as AutoLoanProduct[];
      });

      mortgageService.getProducts().subscribe(x => {
        this.mortgageProducts = x as AutoLoanProduct[];
      });
  }

  async delete(product: CommonProduct, lendingType: LendingType): Promise<void> {
    const answer = await this.dialog.open(ConfirmDialogComponent, {data: new ConfirmData({
      title: 'Удаление',
      buttonName: 'Удалить',
      desription: 'Удалить кредитный продукт ' + product.name + '?'
    })}).afterClosed().toPromise();
    
    if (answer == true) {
      if (lendingType == LendingType.CONSUMER) {
        this.consumerService.delete(product.id);
      }

      if (lendingType == LendingType.AUTO_LOAN) {
        this.autoloanService.delete(product.id);
      }

      if (lendingType == LendingType.MORTGAGE) {
        this.mortgageService.delete(product.id);
      }
    }
  }

  async createProduct(): Promise<void> {
    await this.dialog.open(CreditProductInputComponent).afterClosed().toPromise();
  }
}
