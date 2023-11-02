import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';
import { Widget } from '../models/widget';

@Injectable({
    providedIn: 'root'
})
export class WidgetService {
  private baseUrl = location.protocol + '//' + location.host.slice(0, location.host.indexOf(':')) + ':8081';

  constructor(
      private http: HttpClient,
      private sessionService: SessionService) {
  }

  getWidgets(): Observable<Object> {
      return this.http.get(this.baseUrl + '/api/v1/widget/themes', {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      });
  }

  getWidget(): Observable<Object> {
      return this.http.get(this.baseUrl + '/api/v1/widget/getFirst', {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      });
  }

  addNewTheme(widget: Widget): void {
      this.http.post(this.baseUrl + '/api/v1/widget/themes', widget, {
        headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
      }).subscribe(x => x);
  }

  updateTheme(widget: Widget): void {
    this.http.patch(this.baseUrl + '/api/v1/widget/themes/'+widget.id, widget, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe(x => x);
  }

  deleteTheme(id: number): void {
    this.http.delete(this.baseUrl + '/api/v1/widget/themes/'+id, {
      headers: {Authorization: 'Bearer ' + this.sessionService.getToken()}
    }).subscribe(x => x);
  }
}