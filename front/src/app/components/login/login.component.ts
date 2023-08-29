import { Component } from '@angular/core';
import { VKService } from 'src/app/shared/services/vk-service';
import { SessionService } from '../../shared/services/session.service';
import { Router } from '@angular/router';
import { DataService } from 'src/app/shared/services/data.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(
    public router: Router,
    private vkService: VKService,
    public dataService: DataService,
    public sessionService: SessionService) {
      this.vkService.init();
      const isAuthorized = !!sessionService.getSessionID();
      if (isAuthorized) {
        router.navigate(['']);
      }
  }

  async vkAuthorize(): Promise<void> {
    this.vkService.login();
  }
}
