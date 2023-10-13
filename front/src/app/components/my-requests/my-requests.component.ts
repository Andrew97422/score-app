import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestInputComponent } from './request-input/request-input.component';
import { RegisterService } from 'src/app/shared/services/register-service';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import * as moment from 'moment';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ConfirmData } from '../confirm-dialog/confirm-data.model';
import { SessionService } from 'src/app/shared/services/session.service';
import { DataService } from 'src/app/shared/services/data.service';
import { CardType } from 'src/app/shared/models/card-type';
import { RequestData } from 'src/app/shared/models/request-data';

@Component({
  selector: 'my-requests',
  templateUrl: './my-requests.component.html',
  styleUrls: ['./my-requests.component.css']
})
export class MyRequestsComponent implements OnInit {
  CardType = CardType;
  LendingType = LendingType;
  LendingTypeExt = LendingTypeExt;
  consumers: RequestData[];
  autoLoans: RequestData[];
  mortgages: RequestData[];
  userData: any;
  userCards: any;
  credits: any;
  
  constructor(
    private dialog: MatDialog,
    private dataService: DataService,
    private sessionService: SessionService,
    private registerService: RegisterService) {}

  async ngOnInit(): Promise<void> {
    this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((x: any) => {
      this.userData = x;
      this.userCards = this.dataService.getUserCards(x.login);
      this.credits = this.dataService.getCredits(x.login);
    });

    this.loadRequests();
  }

  getCardNumber(number: string): string {
    return '...' + number.slice(number.length - 4);
  }

  sort(lendingType: LendingType, sortDirection: string): void {
    let items = lendingType == LendingType.CONSUMER ? this.consumers : lendingType == LendingType.AUTO_LOAN ? this.autoLoans : this.mortgages;
    items = sortDirection
      ? items.sort((a, b) => a.applicationDateTime > b.applicationDateTime ? -1 : 1)
      : items.sort((a, b) => a.applicationDateTime > b.applicationDateTime ? 1 : -1);
  }

  async createRequest(): Promise<void> {
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Новая заявка',
      applyButton: 'Создать',
      dialogType: InputDialogType.Create
    })}).afterClosed().toPromise();

    this.loadRequests();
  }

  async viewRequest(data): Promise<void> {
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Свойства заявки',
      dialogType: InputDialogType.View,
      data: data
    })}).afterClosed().toPromise();
  }

  async delete(req): Promise<void> {
    const answer = await this.dialog.open(ConfirmDialogComponent, {data: new ConfirmData({
      title: 'Удаление',
      buttonName: 'Удалить',
      desription: 'Удалить заявку на ' + (req.lendingType == LendingType.MORTGAGE ? 'ипотеку' : LendingTypeExt.getName(req.lendingType).toLowerCase()) 
      + ' от ' + moment(req.applicationDateTime).format('DD.MM.YYYY MM:hh:ss') + '?'
    })}).afterClosed().toPromise();
    
    if (answer == true) {
      this.registerService.deleteRequets(req.id);
      this.loadRequests();
    }
  }

  download(id: number): void {
    this.registerService.downloadPdf(id);
  }

  private loadRequests(): void {
    this.registerService.getRequets(LendingType.AUTO_LOAN).subscribe((x: any) => this.autoLoans = x.applications as RequestData[]);
    this.registerService.getRequets(LendingType.CONSUMER).subscribe((x: any) => this.consumers = x.applications as RequestData[]);
    this.registerService.getRequets(LendingType.MORTGAGE).subscribe((x: any) => this.mortgages = x.applications as RequestData[]);
  }
}
