import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestInputComponent } from './request-input/request-input.component';
import { RegisterService } from 'src/app/shared/services/register-service';
import { LendingType } from 'src/app/shared/models/lending-type';

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
    this.registerService.getRequets(LendingType.AUTO_LOAN).subscribe(x => {this.autoLoans = x});
    this.registerService.getRequets(LendingType.CONSUMER).subscribe(x => {this.consumers = x; console.log(x)});
    this.registerService.getRequets(LendingType.MORTGAGE).subscribe(x => this.mortgages = x);
  }

  async createRequest(): Promise<void> {
    await this.dialog.open(RequestInputComponent).afterClosed().toPromise();
  }
}
