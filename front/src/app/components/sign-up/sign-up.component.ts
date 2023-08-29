import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from 'src/app/shared/services/register-service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  form: FormGroup;

  constructor(
    public router: Router,
    private fb: FormBuilder,
    private registerService: RegisterService,
    public sessionService: SessionService) {
      const isAuthorized = !!sessionService.getSessionID();
      if (isAuthorized) {
        router.navigate(['']);
      }

      this.form = this.fb.group({
        login: [
          null, [Validators.required]
        ],
        password: [
          null, [Validators.required]
        ],
        lastName: [
          null, [Validators.required]
        ],
        firstName: [
          null, [Validators.required]
        ],
        surName: [
          null, [Validators.required]
        ],
        birthday: null,
        phone: null,
        email: [
          null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$/)]
        ]
      });
  }

  signup(): void {
    this.registerService.createUser(this.form.getRawValue());
    this.router.navigate(['login']);
  }
}
