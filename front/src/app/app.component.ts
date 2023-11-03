import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from './shared/services/session.service';
import { RegisterService } from './shared/services/register-service';
     
@Component({
    selector: 'my-app',
    templateUrl: './app.component.html'
})
export class AppComponent {
    isLoaded: boolean;
    constructor(
        public router: Router,
        private sessionService: SessionService,
        private registerService: RegisterService) {
            if (!this.sessionService.getToken()) {
                router.navigate(['login']);
                this.isLoaded = true;
                return;
            }

            this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((x: any) => {
                if (x.role != 'SUPER_ADMIN') router.navigate(['']);
              }
            );
      }
}
