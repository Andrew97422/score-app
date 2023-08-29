import { UserAuthData } from '../models/user-auth-data';
import { Injectable } from '@angular/core';
import { RequestData } from '../models/request-data';
import { RequestStatus } from '../models/request-status';
import { LendingType } from '../models/lending-type';

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

    async getRequets(type: LendingType): Promise<{application: RequestData, status: RequestStatus}[]> {
      const rawResponse = await fetch(this.baseUrl + '/api/v1/application/' + type, {
        method: 'GET',
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