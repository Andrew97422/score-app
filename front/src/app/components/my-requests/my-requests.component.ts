import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestInputComponent } from './request-input/request-input.component';
import { RegisterService } from 'src/app/shared/services/register-service';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import * as moment from 'moment';

@Component({
  selector: 'my-requests',
  templateUrl: './my-requests.component.html',
  styleUrls: ['./my-requests.component.css']
})
export class MyRequestsComponent implements OnInit {
  consumers: any;
  autoLoans: any;
  mortgages:  any;
  
  constructor(
    private dialog: MatDialog,
    private registerService: RegisterService) {}

  async ngOnInit(): Promise<void> {
    this.loadRequests();
  }

  getName(req): string {
    const dateTime = req.applicationDateTime;
    return LendingTypeExt.getName(req.lendingType) + '_' + moment(new Date(dateTime[0], dateTime[1], dateTime[2], dateTime[3], dateTime[4])).format('DD.MM.YYYY_HH:mm:ss');
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
    console.log(data)
    await this.dialog.open(RequestInputComponent, {data: new InputDialogModel({
      title: 'Свойства заявки',
      dialogType: InputDialogType.View,
      data: data.application
    })}).afterClosed().toPromise();
  }

  private loadRequests(): void {
    this.registerService.getRequets(LendingType.AUTO_LOAN).subscribe(x => this.autoLoans = x);
    this.registerService.getRequets(LendingType.CONSUMER).subscribe(x => this.consumers = x);
    this.registerService.getRequets(LendingType.MORTGAGE).subscribe(x => this.mortgages = x);
  }
}
