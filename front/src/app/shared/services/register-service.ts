import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';

@Injectable({ providedIn: 'root'})
export class RegisterService {
    private baseUrl = 'http://localhost:8081';

    createUser(userData: UserAuthData): void {
        (async () => {
            const rawResponse = await fetch(this.baseUrl + '/api/v1/user/register', {
              method: 'POST',
              headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              },
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
            body: JSON.stringify(userData)
          });
          const content = await rawResponse.json();
        
          console.log(content);
      })();
    }

}