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
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';

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
      this.loadProducts();
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
    await this.dialog.open(CreditProductInputComponent, {data: new InputDialogModel({
      title: 'Новый кредитный продукт',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create
    })}).afterClosed().toPromise();

    //this.loadProducts();
  }

  async editProduct(product: CommonProduct, lendingType: LendingType): Promise<void> {
    product.lendingType = lendingType;
    await this.dialog.open(CreditProductInputComponent, {data: new InputDialogModel({
      title: 'Редактирование кредитного продукта',
      applyButton: 'Применить',
      dialogType: InputDialogType.Edit,
      data: product
    })}).afterClosed().toPromise();

    //this.loadProducts();
  }

  private loadProducts(): void {
    this.consumerService.getProducts().subscribe(x => {
      this.consumerProducts = x as ConsumerProduct[];
      this.selectProduct = this.consumerProducts[0];
    });

    this.autoloanService.getProducts().subscribe(x => {
      this.autoLoanProducts = x as AutoLoanProduct[];
    });

    this.mortgageService.getProducts().subscribe(x => {
      this.mortgageProducts = x as AutoLoanProduct[];
    });
  }
}
