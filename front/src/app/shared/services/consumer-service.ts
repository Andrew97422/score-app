import { ConsumerProduct } from '../models/common-product';
import { HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root'})
export class ConsumerService {
    private baseUrl = 'http://91.107.126.118:8081';

    constructor(
        private http: HttpClient,
        private sessionService: SessionService) {
    }

    register(product: ConsumerProduct): void {
        this.http.post(this.baseUrl + '/api/v1/consumer/register', product, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        }).subscribe((x) => console.log(x));
    }

    delete(id: number): Observable<Object> {
        return this.http.delete(this.baseUrl + '/api/v1/consumer/' + id, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        });
    }

    edit(consumerProduct: ConsumerProduct): void {
        this.http.patch(this.baseUrl + '/api/v1/consumer/' + consumerProduct.id, consumerProduct, {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        }).subscribe((x) => console.log(x));
    }

    getProducts(): Observable<Object> {
        return this.http.get(this.baseUrl + '/api/v1/consumer', {
          headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
        });
    }
}
