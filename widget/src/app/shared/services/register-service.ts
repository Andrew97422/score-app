import { Injectable, Injector } from '@angular/core';
import { RequestData } from '../models/request-data';
import { LoginData } from '../models/login-data';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Observable, catchError } from 'rxjs';
import { AuthenticationResponse } from '../models/authentication-response';
import { AuthorizationSource } from '../../shared/models/authorization-source';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DataService } from './data.service';
import { RequestInputComponent } from 'src/app/components/request-input/request-input.component';
import { InputDialogModel, InputDialogType } from '../models/input-dialog-type';
import { CountActiveLoans } from '../models/count-active-loans';
import { MatDialog } from '@angular/material/dialog';

@Injectable({ providedIn: 'root'})
export class RegisterService {
  private baseUrl = location.protocol + '//' + location.host.slice(0, location.host.indexOf(':')) + ':8081';

  constructor(
    private http: HttpClient,
    private dialog: MatDialog,
    private sessionService: SessionService,
    private snackBar: MatSnackBar,
    private inj: Injector) {
  }

  getUser(id: number): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/user/' + id, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }

  login(loginData: LoginData, authorizationSource: AuthorizationSource = AuthorizationSource.None): void {
    const requestCreator = async () => {
      const dataService = this.inj.get(DataService);
      const creditCount = dataService.getCredits();
      dataService.dialogRef?.close();
      await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
        title: 'Новая заявка',
        applyButton: 'Создать',
        dialogType: InputDialogType.Create,
        data: {
          creditAmount: dataService.data?.creditAmount,
          lendingType: dataService.data?.lendingType,
          amountLoanPayments: dataService.data?.amountLoanPayments,
          psbClient: authorizationSource == AuthorizationSource.PSB,
          countActiveLoans: (creditCount > 0 && creditCount <= 2) ? CountActiveLoans.FROM_ONE_TO_TWO 
          : (creditCount > 2 && creditCount <= 5) ? CountActiveLoans.FROM_THREE_TO_FIVE
          : (creditCount > 5) ? CountActiveLoans.MORE_THAN_FIVE
          : CountActiveLoans.NO_CREDITS
        }
      })}).afterClosed().toPromise();
    }

    this.http.post(this.baseUrl + '/api/v1/login', loginData)
      .pipe(
        catchError(async (err) => {
          this.snackBar.open(`Неправильное имя пользователя или пароль`, 'OK', { duration: 36000 });
          throw err;
        })
      )
      .subscribe(async (x: AuthenticationResponse) => {
        this.sessionService.createSession(x.id, true, x.token, authorizationSource);
        
        await requestCreator();
      });

  }
  
  sendRequest(userData: RequestData): void {
    this.http.post(this.baseUrl + '/api/v1/application/register', userData, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe(() => this.snackBar.open(`Заявка на кредит на сумму ${userData.amount} отправлена успешно`, 'OK', { duration: 36000 }));
  }

  sendRequestWithoutAuth(userData: RequestData): void {
    this.http.post(this.baseUrl + '/api/v1/application/noauth/register', userData)
    .subscribe(() => this.snackBar.open(`Заявка на кредит на сумму ${userData.amount} отправлена успешно`, 'OK', { duration: 36000 }));
  }
}
