import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';
import { LendingType } from '../models/lending-type';
import { LoginData } from '../models/login-data';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../models/authentication-response';
import * as moment from 'moment';

@Injectable({ providedIn: 'root'})
export class RegisterService {
  private baseUrl = 'http://localhost:8081';

  constructor(
    private router: Router,
    private http: HttpClient,
    private sessionService: SessionService) {
  }

  getUser(id: number): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/user/' + id, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }

  login(loginData: LoginData): void {
    this.http.post(this.baseUrl + '/api/v1/login', loginData).subscribe((x: AuthenticationResponse) => {
      console.log(x);
      this.router.navigate(['']);
      this.sessionService.createSession(x.id, true, x.token);
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
    this.http.patch(this.baseUrl + '/api/v1/user/update/' + id, userData, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe();
  }
  
  sendRequest(userData: RequestData): void {
    this.http.post(this.baseUrl + '/api/v1/application/register', userData, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe(x => console.log(x));
  }

  sendRequestWithoutAuth(userData: RequestData): void {
    this.http.post(this.baseUrl + '/api/v1/application/noauth/register', userData).subscribe(x => console.log(x));
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
    this.http.delete(this.baseUrl + '/api/v1/application/delete', {params: { id }, headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}})
    .subscribe(x => x);
  }

  downloadPdf(id: number): void {
    this.http.get(
      this.baseUrl + '/api/v1/application/create_pdf', {
        responseType: 'blob',
        params: { id },
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
