import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  getSessionID(): string {
    return localStorage.getItem('id');
  }

  getSessionStatus(): string {
    return localStorage.getItem('isLoggedIn');
  }

  createSession(id, status): void {
    localStorage.setItem('id', id);
    localStorage.setItem('isLoggedIn', status);
  }

  destroySession(): void {
    localStorage.clear();
  }
}