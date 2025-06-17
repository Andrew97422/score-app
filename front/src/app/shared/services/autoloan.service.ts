import { AutoLoanProduct } from '../models/common-product';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class AutoloanService {
    private baseUrl = location.protocol + '//' + location.host.slice(0, location.host.indexOf(':')) + ':8084';

    constructor(
        private http: HttpClient,
        private sessionService: SessionService) {
    }

    register(product: AutoLoanProduct): void {
        this.http.post(this.baseUrl + '/api/v1/autoloan/register', product, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        }).subscribe((x) => console.log(x));
    }

    delete(id: number): Observable<Object> {
      return this.http.delete(this.baseUrl + '/api/v1/autoloan/' + id, {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      });
    }

    edit(product: AutoLoanProduct): void {
      this.http.patch(this.baseUrl + '/api/v1/autoloan/' + product.id, product, {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      }).subscribe((x) => console.log(x));
    }

    getProducts(): Observable<Object> {
        return this.http.get(this.baseUrl + '/api/v1/autoloan', {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        });
    }
}
