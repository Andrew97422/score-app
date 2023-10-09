import { Component } from '@angular/core';
import { VKService } from 'src/app/shared/services/vk-service';
import { SessionService } from '../../shared/services/session.service';
import { Router } from '@angular/router';
import { DataService } from 'src/app/shared/services/data.service';
import { RegisterService } from 'src/app/shared/services/register-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PsbAuthorizationComponent } from '../psb-authorization/psb-authorization.component';
import { MatDialog } from '@angular/material/dialog';
import { AuthorizationSource } from 'src/app/shared/models/authorization-source';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    public router: Router,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private vkService: VKService,
    public dataService: DataService,
    public sessionService: SessionService,
    public registerService: RegisterService) {
      this.vkService.init();
      
      const isAuthorized = !!sessionService.getSessionID();
      if (isAuthorized) {
        router.navigate(['']);
      }

      this.form = this.fb.group({
        username: [
          null, [Validators.required]
        ],
        password: [
          null, [Validators.required]
        ]
      });
  }

  async login(): Promise<void> {
    this.registerService.login(this.form.getRawValue(), AuthorizationSource.None, true);
  }

  psbAuthorize(): void {
    this.dialog.open(PsbAuthorizationComponent);
  }

  async vkAuthorize(): Promise<void> {
    this.vkService.login();
  }
}
