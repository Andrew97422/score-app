import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ShopComponent } from './components/shop/shop.component';
import { ShopPrototypeComponent } from './components/shop/shop-prototype/shop-prototype.component';
import { ProductComponent } from './components/shop/shop-prototype/product/product.component';
import { CreditProductsComponent } from './components/credit-products/credit-products.component';
import { MyRequestsComponent } from './components/my-requests/my-requests.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: '', component: MyRequestsComponent },
  { path: 'shop', component: ShopComponent },
  { path: 'prototype', component: ShopPrototypeComponent },
  { path: 'product', component: ProductComponent},
  { path: 'creditProducts', component: CreditProductsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
