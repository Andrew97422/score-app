import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';
import { LendingType } from '../models/lending-type';
import { LoginData } from '../models/login-data';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class RegisterService {
    private baseUrl = 'http://localhost:8081';

    constructor(
      private router: Router,
      private http: HttpClient,
      private sessionService: SessionService) {
    }

    getUser(id: number): Observable<Object> {
      return this.http.get(this.baseUrl + '/api/v1/user/' + id);
    }

    login(loginData: LoginData): void {
      this.http.post(this.baseUrl + '/api/v1/user/login', loginData).subscribe(x =>
        this.router.navigate(['']));
    }

    createUser(userData: UserAuthData): void {
      this.http.post(this.baseUrl + '/api/v1/user/register', userData).subscribe(id => {
        this.sessionService.createSession(id, true);
      });
    }

    editUser(id: number, userData: UserAuthData): void {
      this.http.patch(this.baseUrl + '/api/v1/user/update/' + id, userData).subscribe();
    }
    
    sendRequest(userData: RequestData): void {
      this.http.post(this.baseUrl + '/api/v1/application/register', userData).subscribe(x => console.log(x));
    }

    sendRequestWithoutAuth(userData: RequestData): void {
      this.http.post(this.baseUrl + '/api/v1/application/noauth/register', userData).subscribe(x => console.log(x));
    }

    getRequets(type: LendingType): Observable<Object> {
      return this.http.get(this.baseUrl + '/api/v1/application/' + type);
    }
}