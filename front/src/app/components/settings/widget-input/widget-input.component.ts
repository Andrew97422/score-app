import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WidgetService } from '../../../shared/services/widget-service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InputDialogModel } from '../../../shared/models/input-dialog-type';
import { FontFamily, FontFamilyExt } from '../../../shared/models/font-family';

@Component({
  selector: 'widget-input',
  templateUrl: './widget-input.component.html',
  styleUrls: ['./widget-input.component.css']
})
export class WidgetInputComponent {
  FontFamilyExt=FontFamilyExt;

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private widgetService: WidgetService,
    private dialogRef: MatDialogRef<WidgetInputComponent>,
    @Inject(MAT_DIALOG_DATA) public data: InputDialogModel<any>) {
      this.form = this.fb.group({
        name: null,
        fontFamily: FontFamily.Roboto,
        color1: null,
        color: 'rgba(43, 44, 132)'
      });

      this.form.controls.color1.valueChanges.subscribe(x => this.form.controls.color.setValue(x));
      this.form.controls.color.valueChanges.subscribe(x => this.form.controls.color1.setValue(x));
      
  }

  submit(): void {
    const result = this.form.getRawValue();
    this.dialogRef.close();
  }
}
