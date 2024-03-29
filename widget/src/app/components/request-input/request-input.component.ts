import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RegisterService } from 'src/app/shared/services/register-service';
import { WorkExperienceExt } from 'src/app/shared/models/work-experience';
import { CountActiveLoans, CountActiveLoansExt } from 'src/app/shared/models/count-active-loans';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InputDialogModel, InputDialogType } from '../../shared/models/input-dialog-type';

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
    private registerService: RegisterService,
    private dialogRef: MatDialogRef<RequestInputComponent>,
    @Inject(MAT_DIALOG_DATA) public data: InputDialogModel<any>) {
      this.form = this.fb.group({
        user: null,
        lendingType: null,
        workExperience: null,
        countActiveLoans: null,
        currentDebtLoad: null,
        monthlyIncome: null,
        amount: null,
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
        this.form.controls.user.setValue(data.data.user);
        this.form.controls.amount.setValue(data.data.creditAmount);
        this.form.controls.lendingType.setValue(data.data.lendingType);
        this.form.controls.workExperience.setValue(data.data.workExperience);
        this.form.controls.countActiveLoans.setValue(data.data.countActiveLoans);
        this.form.controls.currentDebtLoad.setValue(data.data.amountLoanPayments);
        this.form.controls.monthlyIncome.setValue(data.data.monthlyIncome);
        this.form.controls.term.setValue(data.data.term);
        this.form.controls.military.setValue(data.data.military);
        this.form.controls.stateEmployee.setValue(data.data.stateEmployee);
        this.form.controls.psbClient.setValue(data.data.psbClient);
        this.form.controls.farEastInhabitant.setValue(data.data.farEastInhabitant);
        this.form.controls.newSubjectsResident.setValue(data.data.newSubjectsResident);
        this.form.controls.itSpecialist.setValue(data.data.itSpecialist);

        if (data.data.countActiveLoans == CountActiveLoans.NO_CREDITS) {
          this.form.controls.currentDebtLoad.setValue(0);
          this.form.controls.currentDebtLoad.disable();
        }
      }

      if (data.dialogType == InputDialogType.View) {
        this.form.disable();
      }

      this.form.controls.countActiveLoans.valueChanges.subscribe(x => {
        if (x == CountActiveLoans.NO_CREDITS) {
          this.form.controls.currentDebtLoad.setValue(0);
          this.form.controls.currentDebtLoad.disable();
        } else {
          this.form.controls.currentDebtLoad.enable();
        }
      });
  }

  submit(): void {
    const result = this.form.getRawValue();
    this.registerService.sendRequest(result);
    this.dialogRef.close();
  }
}
