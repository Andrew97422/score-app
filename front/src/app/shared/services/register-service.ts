import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';
import { RequestStatus } from '../models/request-status';
import { LendingType } from '../models/lending-type';
import { LoginData } from '../models/login-data';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root'})
export class RegisterService {
    private baseUrl = 'http://localhost:8081';

    constructor(private router: Router) {
    }

    login(loginData: LoginData): void {
      (async () => {
          const rawResponse = await fetch(this.baseUrl + '/api/v1/user/register', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            mode: 'no-cors',
            body: JSON.stringify(loginData)
          });
          const content = await rawResponse.json();
          this.router.navigate(['']);
          console.log(content);
      })();
    }

    createUser(userData: UserAuthData): void {
        (async () => {
            const rawResponse = await fetch(this.baseUrl + '/api/v1/user/register', {
              method: 'POST',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
              mode: 'no-cors',
              body: JSON.stringify(userData)
            });
            const content = await rawResponse.json();
          
            console.log(content);
        })();
    }
    
    sendRequest(userData: RequestData): void {
      (async () => {
          const rawResponse = await fetch(this.baseUrl + '/api/v1/application/register', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            mode: 'no-cors',
            body: JSON.stringify(userData)
          });
          const content = await rawResponse.json();
        
          console.log(content);
      })();
    }

    sendRequestWithoutAuth(userData: RequestData): void {
      (async () => {
          const rawResponse = await fetch(this.baseUrl + '/api/v1/application/noauth/register', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            mode: 'no-cors',
            body: JSON.stringify(userData)
          });
          const content = await rawResponse.json();
        
          console.log(content);
      })();
    }

    async getRequets(type: LendingType): Promise<{application: RequestData, status: RequestStatus}[]> {
      const rawResponse = await fetch(this.baseUrl + '/api/v1/application/' + type, {
        method: 'GET',
        mode: 'no-cors',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      });
      const content = await rawResponse.json();
    
      console.log(content);
      return JSON.parse(content);
    }
}