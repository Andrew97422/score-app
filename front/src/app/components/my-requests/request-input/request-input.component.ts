import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from 'src/app/shared/services/register-service';
import { WorkExperienceExt } from 'src/app/shared/models/work-experience';
import { LoanCollateralTypeExt } from 'src/app/shared/models/loan-collateral-type';
import { CountActiveLoansExt } from 'src/app/shared/models/count-active-loans';

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
    private registerService: RegisterService) {
      this.form = this.fb.group({
        description: [
          null, [Validators.required]
        ],
        email: 'a@a.a',
        workExperience: null,
        loanCollateralType: null,
        countActiveLoans: null,
        currentDebtLoad: null,
        birthdamonthlyIncomey: null,
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
        itSpecialist: null,
        birthday: null
      });
  }

  submit(): void {
    this.registerService.sendRequest(this.form.getRawValue());
  }
}
