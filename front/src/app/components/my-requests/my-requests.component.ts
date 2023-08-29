import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestInputComponent } from './request-input/request-input.component';

@Component({
  selector: 'my-requests',
  templateUrl: './my-requests.component.html',
  styleUrls: ['./my-requests.component.css']
})
export class MyRequestsComponent {
  constructor(private dialog: MatDialog) {

  }

  async createRequest(): Promise<void> {
    await this.dialog.open(RequestInputComponent).afterClosed().toPromise();
  }
}
