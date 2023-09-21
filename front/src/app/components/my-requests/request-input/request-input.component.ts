import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from 'src/app/shared/services/register-service';
import { WorkExperienceExt } from 'src/app/shared/models/work-experience';
import { CountActiveLoansExt } from 'src/app/shared/models/count-active-loans';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InputDialogModel, InputDialogType } from '../../../shared/models/input-dialog-type';
import { Router } from '@angular/router';

@Component({
  selector: 'request-input',
  templateUrl: './request-input.component.html',
  styleUrls: ['./request-input.component.css']
})
export class RequestInputComponent {
  InputDialogType = InputDialogType;
  WorkExperienceExt = WorkExperienceExt;
  CountActiveLoansExt = CountActiveLoansExt;
  LendingTypeExt = LendingTypeExt;
  LendingType = LendingType;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private registerService: RegisterService,
    private dialogRef: MatDialogRef<RequestInputComponent>,
    @Inject(MAT_DIALOG_DATA) public data: InputDialogModel<any>) {
      this.form = this.fb.group({
        lendingType: [
          null, [Validators.required]
        ],
        workExperience: [
          null, [Validators.required]
        ],
        countActiveLoans: [
          null, [Validators.required]
        ],
        currentDebtLoad: [
          null, [Validators.min(0)]
        ],
        monthlyIncome: [
          null, [Validators.min(0)]
        ],
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

      if (data.data) {
        this.form.controls.amount.setValue(data.data.creditAmount);
        this.form.controls.lendingType.setValue(data.data.lendingType);
        this.form.controls.workExperience.setValue(data.data.workExperience?.name);
        this.form.controls.countActiveLoans.setValue(data.data.currentDebtLoad?.countActiveLoans);
        this.form.controls.currentDebtLoad.setValue(data.data.currentDebtLoad?.countActiveLoans);
        this.form.controls.monthlyIncome.setValue(data.data.monthlyIncome);
        this.form.controls.term.setValue(data.data.term);
        this.form.controls.military.setValue(data.data.military);
        this.form.controls.stateEmployee.setValue(data.data.stateEmployee);
        this.form.controls.psbClient.setValue(data.data.psbClient);
        this.form.controls.farEastInhabitant.setValue(data.data.farEastInhabitant);
        this.form.controls.newSubjectsResident.setValue(data.data.newSubjectsResident);
        this.form.controls.itSpecialist.setValue(data.data.itSpecialist);
      }

      if (data.dialogType == InputDialogType.View) {
        this.form.disable();
      }
  }

  submit(): void {
    const result = this.form.getRawValue();
    this.registerService.sendRequest(result);
    this.dialogRef.close();
    this.router.navigate(['']);
  }
}
