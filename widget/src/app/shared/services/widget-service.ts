import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class WidgetService {
  private baseUrl = location.protocol + '//' + location.host.slice(0, location.host.indexOf(':')) + ':8081';

  constructor(
    private http: HttpClient,
    private sessionService: SessionService) {
  }

  getWidget(): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/widget/getFirst', {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }

  getTheme(id: number): Observable<Object> {
    return this.http.get(this.baseUrl + '/api/v1/widget/themes/' + id, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    });
  }
}