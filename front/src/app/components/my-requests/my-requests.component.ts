import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestInputComponent } from './request-input/request-input.component';
import { RegisterService } from 'src/app/shared/services/register-service';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import * as moment from 'moment';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ConfirmData } from '../confirm-dialog/confirm-data.model';

@Component({
  selector: 'my-requests',
  templateUrl: './my-requests.component.html',
  styleUrls: ['./my-requests.component.css']
})
export class MyRequestsComponent implements OnInit {
  LendingType = LendingType;
  LendingTypeExt = LendingTypeExt;
  consumers: any;
  autoLoans: any;
  mortgages:  any;
  
  constructor(
    private dialog: MatDialog,
    private registerService: RegisterService) {}

  async ngOnInit(): Promise<void> {
    this.loadRequests();
  }

  getDateTime(req): string {
    const dateTime = req.applicationDateTime;
    return moment(new Date(dateTime[0], dateTime[1]-1, dateTime[2], dateTime[3], dateTime[4])).format('DD.MM.YYYY HH:mm:ss');
  }

  sort(lendingType: LendingType, sortDirection: string): void {
    let items = lendingType == LendingType.CONSUMER ? this.consumers : lendingType == LendingType.AUTO_LOAN ? this.autoLoans : this.mortgages;
    items = sortDirection
      ? items.sort((a, b) => this.getDateTime(a.application) > this.getDateTime(b.application) ? -1 : 1)
      : items.sort((a, b) => this.getDateTime(a.application) > this.getDateTime(b.application) ? 1 : -1);
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
      data: data.application
    })}).afterClosed().toPromise();
  }

  async delete(req): Promise<void> {
    const answer = await this.dialog.open(ConfirmDialogComponent, {data: new ConfirmData({
      title: 'Удаление',
      buttonName: 'Удалить',
      desription: 'Удалить ' + LendingTypeExt.getName(req.lendingType) + ' от ' + this.getDateTime(req) + '?'
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
    this.registerService.getRequets(LendingType.AUTO_LOAN).subscribe((x: any) => this.autoLoans = x.applications);
    this.registerService.getRequets(LendingType.CONSUMER).subscribe((x: any) => this.consumers = x.applications);
    this.registerService.getRequets(LendingType.MORTGAGE).subscribe((x: any) => this.mortgages = x.applications);
  }
}
