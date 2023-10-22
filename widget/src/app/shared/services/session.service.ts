import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  getToken() {
    return localStorage.getItem('token');
  }

  getAuthorizationSource() {
    return +localStorage.getItem('authorizationSource');
  }

  getSessionID(): string {
    return localStorage.getItem('id');
  }

  getSessionStatus(): string {
    return localStorage.getItem('isLoggedIn');
  }

  createSession(id, status, token, authorizationSource): void {
    localStorage.setItem('id', id);
    localStorage.setItem('isLoggedIn', status);
    localStorage.setItem('token', token);
    localStorage.setItem('authorizationSource', authorizationSource);
  }

  destroySession(): void {
    localStorage.clear();
  }
}