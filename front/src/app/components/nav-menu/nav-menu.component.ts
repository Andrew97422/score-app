import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from 'src/app/shared/services/data.service';
import { SessionService } from 'src/app/shared/services/session.service';
import { VKService } from 'src/app/shared/services/vk-service';

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
    public dataService: DataService,
    public sessionService: SessionService,
    private vkService: VKService) {
      this.userData = dataService.getUserProfile();
  }

  logout(): void {
    this.vkService.logout();
  }
}
