import { AutoLoanProduct } from '../models/common-product';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class MortgageService {
    private baseUrl = 'http://localhost:8081';

    constructor(
        private http: HttpClient,
        private sessionService: SessionService) {
    }

    register(product: AutoLoanProduct): void {
        this.http.post(this.baseUrl + '/api/v1/mortgage/register', product, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        }).subscribe((x) => console.log(x));
    }

    delete(id: number): void {
      this.http.delete(this.baseUrl + '/api/v1/mortgage/' + id, {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      }).subscribe((x) => console.log(x));
    }

    getProducts(): Observable<Object> {
        return this.http.get(this.baseUrl + '/api/v1/mortgage', {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        });
    }
}
