import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';
import { LendingType } from '../models/lending-type';
import { LoginData } from '../models/login-data';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Observable, catchError } from 'rxjs';
import { AuthenticationResponse } from '../models/authentication-response';
import { AuthorizationSource } from '../../shared/models/authorization-source';
import * as moment from 'moment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({ providedIn: 'root'})
export class RegisterService {
  private baseUrl = location.protocol + '//' + location.host.slice(0, location.host.indexOf(':')) + ':8081';

  constructor(
    private router: Router,
    private http: HttpClient,
    private sessionService: SessionService,
    private snackBar: MatSnackBar) {
  }

  getUser(id: number): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/user/' + id, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }

  login(loginData: LoginData, authorizationSource: AuthorizationSource = AuthorizationSource.None, router = false): void {
    this.http.post(this.baseUrl + '/api/v1/login', loginData)
    .pipe(
      catchError(async (err) => {
        this.snackBar.open(`Неправильное имя пользователя или пароль`, 'OK', { duration: 36000 });
        throw err;
      })
    )
    .subscribe(async (x: AuthenticationResponse) => {
      this.sessionService.createSession(x.id, true, x.token, authorizationSource);
      if (router)
        this.router.navigate(['']);
    });
  }

  logout(): void {
    this.router.navigate(['login']);
    this.sessionService.destroySession();
  }

  createUser(userData: UserAuthData): void {
    this.http.post(this.baseUrl + '/api/v1/register', userData).subscribe(id => console.log(id));
  }

  editUser(id: number, userData: UserAuthData): void {
    this.http.patch(this.baseUrl + '/api/v1/user/' + id, userData, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe();
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

  getRequets(type: LendingType): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/application', {
      params: {
        type
      },
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }

  deleteRequets(id: number): void {
    this.http.delete(this.baseUrl + '/api/v1/application/' + id, {headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}})
    .subscribe(x => x);
  }

  downloadPdf(id: number): void {
    this.http.get(
      this.baseUrl + `/api/v1/application/${id}/create_pdf`, {
        responseType: 'blob',
        headers: { Authorization: 'Bearer ' + this.sessionService.getToken() }
      })
    .subscribe((blob) => {
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = `Предложения по кредитам ${moment().format('YYYY.MM.DD_HH.ss')}.pdf`;
      link.click();
    });
  }
}
