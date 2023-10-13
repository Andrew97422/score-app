import { Component } from '@angular/core';
import { RegisterService } from 'src/app/shared/services/register-service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  user: any;

  constructor(
    private sessionService: SessionService,
    private registerService: RegisterService) {
    this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((user: any) => this.user = user);
  }
}
