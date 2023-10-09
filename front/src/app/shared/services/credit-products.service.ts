import { AutoLoanProduct } from '../models/common-product';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root'})
export class CreditProductService {
    private baseUrl = 'http://localhost:8081';

    constructor(
        private http: HttpClient,
        private sessionService: SessionService) {
    }

    register(product: AutoLoanProduct): void {
        this.http.post(this.baseUrl + '/api/v1/autoloan/register', product, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        }).subscribe((x) => console.log(x));
    }
}
