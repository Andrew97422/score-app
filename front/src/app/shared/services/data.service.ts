import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  storeUserProfile(user: any): void {
    localStorage.setItem('profile', JSON.stringify(user));
  }

  getUserProfile(): any {
    return JSON.parse(localStorage.getItem('profile'));
  }
}
