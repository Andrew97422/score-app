import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { 
    MatRippleModule,
    MAT_DATE_LOCALE,
    MatNativeDateModule
} from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';

import { ShopComponent } from './components/shop/shop.component';
import { LoginComponent } from './components/login/login.component';
import { RequestInputComponent } from './components/request-input/request-input.component';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogConfig, MatDialogModule } from '@angular/material/dialog';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { InternalServerErrorInterceptor } from './shared/services/internal-server-error-interceptor';
import { PsbAuthorizationComponent } from './components/psb-authorization/psb-authorization.component';

@NgModule({
    imports: [ 
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        AppRoutingModule,
        ReactiveFormsModule,
        FlexLayoutModule,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
        MatRippleModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatSelectModule,
        MatCheckboxModule,
        MatDialogModule,
        MatTooltipModule,
        HttpClientModule,
        MatSnackBarModule,
        MatTabsModule
    ],
    declarations: [
        AppComponent,
        LoginComponent,
        RequestInputComponent,
        ShopComponent,
        PsbAuthorizationComponent,
    ],
    bootstrap:    [ AppComponent ],
    providers: [
        { provide: MAT_DATE_LOCALE, useValue: 'ru-RU' },
        { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { ...new MatDialogConfig(), disableClose: true } },
        { provide: HTTP_INTERCEPTORS, useClass: InternalServerErrorInterceptor, multi: true }
    ]  
})
export class AppModule { }
