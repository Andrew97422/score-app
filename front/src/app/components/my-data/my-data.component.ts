import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { RegisterService } from 'src/app/shared/services/register-service';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'my-data',
  templateUrl: './my-data.component.html',
  styleUrls: ['./my-data.component.css']
})
export class MyDataComponent {
  userData: Object;
  savedModel: any;

  form: FormGroup;
  readonlyView = true;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private sessionService: SessionService) {
      this.form = this.fb.group({
        login: null,
        password: null,
        lastName: [
          null, [Validators.required]
        ],
        firstName: [
          null, [Validators.required]
        ],
        surName: [
          null, [Validators.required]
        ],
        birthday: [
          null, [Validators.required]
        ],
        phone: null,
        email: [
          null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$/)]
        ]
      });
      
    this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((x: any) => {
      this.form.controls.login.setValue(x.login);
      this.form.controls.password.setValue(x.password);
      this.form.controls.lastName.setValue(x.lastName);
      this.form.controls.firstName.setValue(x.firstName);
      this.form.controls.surName.setValue(x.surName);
      this.form.controls.birthday.setValue(new Date(x.birthday));
      this.form.controls.phone.setValue(x.phone);
      this.form.controls.email.setValue(x.email);
      this.savedModel = this.form.getRawValue();
    });
  }

  submit(): void {
    const result = this.form.getRawValue();
    result.birthday = moment(result.birthday).format('DD.MM.YYYY');
    this.registerService.editUser(this.sessionService.getSessionID() as unknown as number, result);
  }
  
  rollback(): void {
    this.form.controls.login.setValue(this.savedModel.login);
    this.form.controls.password.setValue(this.savedModel.password);
    this.form.controls.lastName.setValue(this.savedModel.lastName);
    this.form.controls.firstName.setValue(this.savedModel.firstName);
    this.form.controls.surName.setValue(this.savedModel.surName);
    this.form.controls.birthday.setValue(this.savedModel.birthday);
    this.form.controls.phone.setValue(this.savedModel.phone);
    this.form.controls.email.setValue(this.savedModel.email);
  }
}
