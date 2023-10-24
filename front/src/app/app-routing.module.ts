import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { CreditProductsComponent } from './components/credit-products/credit-products.component';
import { MyRequestsComponent } from './components/my-requests/my-requests.component';
import { SettingsComponent } from './components/settings/settings.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: '', component: MyRequestsComponent },
  { path: 'creditProducts', component: CreditProductsComponent },
  { path: 'settings', component: SettingsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
