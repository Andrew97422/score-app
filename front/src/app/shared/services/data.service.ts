import { Injectable } from '@angular/core';
import { CardType } from '../models/card-type';
import { SessionService } from './session.service';
import { AuthorizationSource } from '../models/authorization-source';
import { RegisterService } from './register-service';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  userCards = [
    {
      login: 'user',
      amount: '100 099',
      type: CardType.Debit,
      number: '0000 0000 0000 1247',
    },
    {
      login: 'user',
      amount: '1 000 001',
      type: CardType.Credit,
      number: '0000 0000 0000 1041'
    }
  ];

  credits = [
    {
      login: 'user',
      name: 'Ипотека СЖ с господдержкой 2020',
      amount: '1 500 499',
      monthlyPaymentAmount:  20000
    }
  ]

  constructor(
    private registerService: RegisterService,
    private sessionService: SessionService) {

  }

  storeUserProfile(user: any): void {
    localStorage.setItem('profile', JSON.stringify(user));
  }

  getUserProfile(): any {
    return JSON.parse(localStorage.getItem('profile'));
  }

  getCredits(login?: string): any {
    let userLogin = login ?? this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((x: any) => userLogin = x.login);
    return this.sessionService.getAuthorizationSource() == AuthorizationSource.PSB ? this.credits.filter(c => c.login == userLogin) : [];
  }

  getUserCards(login: string): any {
    return this.sessionService.getAuthorizationSource() == AuthorizationSource.PSB ? this.userCards.filter(c => c.login == login) : [];
  }
}
