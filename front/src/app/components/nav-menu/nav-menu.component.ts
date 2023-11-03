import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DataService } from 'src/app/shared/services/data.service';
import { RegisterService } from 'src/app/shared/services/register-service';
import { SessionService } from 'src/app/shared/services/session.service';
import { VKService } from 'src/app/shared/services/vk-service';
import { MyDataComponent } from '../my-data/my-data.component';

@Component({
  selector: 'nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent {
  userName='root';
  isAuthorized=false;
  userData: any;

  constructor(
    public router: Router,
    private dialog: MatDialog,
    public dataService: DataService,
    public sessionService: SessionService,
    private vkService: VKService,
    private registerService: RegisterService) {
      this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe(x =>
        this.userData = x);
  }

  downloadHelp(): void {
    window.open('/src/assets/help.pdf', '_blank');
  }

  logout(): void {
    this.vkService.logout();
  }

  async viewUserData(): Promise<void> {
    await this.dialog.open(MyDataComponent).afterClosed().toPromise();
  }
}
