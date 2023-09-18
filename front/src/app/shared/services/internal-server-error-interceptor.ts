import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http'
import { Observable, catchError, throwError } from 'rxjs';
import { RegisterService } from '../services/register-service';

@Injectable()
export class InternalServerErrorInterceptor implements HttpInterceptor {
    constructor(private registerService: RegisterService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status > 400 && err.status < 500 ) {
                //this.registerService.logout();
            }

            return throwError(err);
        }))
    }
}