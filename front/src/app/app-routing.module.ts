import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ShopComponent } from './components/shop/shop.component';
import { ShopPrototypeComponent } from './components/shop/shop-prototype/shop-prototype.component';
import { ProductComponent } from './components/shop/shop-prototype/product/product.component';
import { HomeComponent } from './components/home/home.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: '', component: HomeComponent },
  { path: 'shop', component: ShopComponent },
  { path: 'prototype', component: ShopPrototypeComponent },
  { path: 'product', component: ProductComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
