import { AfterViewInit, Component } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from 'src/app/shared/services/data.service';
import { RegisterService } from 'src/app/shared/services/register-service';
import { SessionService } from 'src/app/shared/services/session.service';
import { VKService } from 'src/app/shared/services/vk-service';

@Component({
  selector: 'nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.css']
})
export class NavMenuComponent implements AfterViewInit {
  userName='root';
  isAuthorized=false;
  userData: any;

  constructor(
    public router: Router,
    public dataService: DataService,
    public sessionService: SessionService,
    private vkService: VKService,
    private registerService: RegisterService) {
  }

  ngAfterViewInit(): void {
    this.userData = this.dataService.getUserProfile();
    this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe(x =>
      this.userData = x);
  }

  logout(): void {
    this.vkService.logout();
  }
}
