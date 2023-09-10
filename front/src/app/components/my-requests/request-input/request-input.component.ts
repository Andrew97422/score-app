import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from 'src/app/shared/services/register-service';
import { WorkExperienceExt } from 'src/app/shared/models/work-experience';
import { LoanCollateralTypeExt } from 'src/app/shared/models/loan-collateral-type';
import { CountActiveLoansExt } from 'src/app/shared/models/count-active-loans';
import * as moment from 'moment';
import { SessionService } from 'src/app/shared/services/session.service';

@Component({
  selector: 'request-input',
  templateUrl: './request-input.component.html',
  styleUrls: ['./request-input.component.css']
})
export class RequestInputComponent {
  WorkExperienceExt = WorkExperienceExt;
  LoanCollateralTypeExt = LoanCollateralTypeExt;
  CountActiveLoansExt = CountActiveLoansExt;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private sessionService: SessionService) {
      this.form = this.fb.group({
        email: [
          null, [Validators.required]
        ],
        birthday: [
          null, [Validators.required]
        ],
        workExperience: null,
        loanCollateralType: null,
        countActiveLoans: null,
        currentDebtLoad: null,
        amount: [
          null, [Validators.required]
        ],
        term: null,
        minRate: null,
        maxRate: null,
        military: null,
        stateEmployee: null,
        psbClient: null,
        farEastInhabitant: null,
        newSubjectsResident: null,
        itSpecialist: null
      });

      this.registerService.getUser(this.sessionService.getSessionID() as unknown as number).subscribe((x: any) => {
        this.form.controls.birthday.setValue(new Date(x.birthday));
        this.form.controls.email.setValue(x.email);
      });
  }

  submit(): void {
    const result = this.form.getRawValue();
    result.birthday = moment(result.birthday).format('DD.MM.YYYY');
    this.registerService.sendRequest(result);
  }
}
