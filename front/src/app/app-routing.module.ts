import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { MyRequestsComponent } from './components/my-requests/my-requests.component';
import { ShopComponent } from './components/shop/shop.component';
import { MyDataComponent } from './components/my-data/my-data.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: '', component: MyRequestsComponent },
  { path: 'shop', component: ShopComponent },
  { path: 'myData', component: MyDataComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
