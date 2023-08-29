import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { MyRequestsComponent } from './components/my-requests/my-requests.component';
import { ProfileComponent } from './components/profile/profile.component';
import { MyDataComponent } from './components/my-data/my-data.component';
import { ShopComponent } from './components/shop/shop.component';

const routes: Routes = [
  { path: '', component: ProfileComponent },
  { path: 'myData', component: MyDataComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'myRequests', component: MyRequestsComponent },
  { path: 'shop', component: ShopComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
