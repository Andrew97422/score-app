import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  getToken() {
    return localStorage.getItem('token');
  }

  getSessionID(): string {
    return localStorage.getItem('id');
  }

  getSessionStatus(): string {
    return localStorage.getItem('isLoggedIn');
  }

  createSession(id, status, token): void {
    localStorage.setItem('id', id);
    localStorage.setItem('isLoggedIn', status);
    localStorage.setItem('token', token);
  }

  destroySession(): void {
    localStorage.clear();
  }
}