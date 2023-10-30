import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WidgetService } from '../../../shared/services/widget-service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { InputDialogModel, InputDialogType } from '../../../shared/models/input-dialog-type';
import { FontFamilyExt } from '../../../shared/models/font-family';

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
        id: null,
        name: null,
        font: 'Roboto, sans-serif',
        color1: 'rgba(43, 44, 132)',
        color: 'rgba(43, 44, 132)'
      });

      this.form.controls.color1.valueChanges.subscribe(x => this.form.controls.color.setValue(x));
      //this.form.controls.color.valueChanges.subscribe(x => this.form.controls.color1.setValue(x));

      if (data.dialogType == InputDialogType.Edit) {
        this.form.controls.id.setValue(data.data.id);
        this.form.controls.name.setValue(data.data.name);
        this.form.controls.font.setValue(data.data.font);
        this.form.controls.color.setValue(data.data.color);
        this.form.controls.color1.setValue(data.data.color);
      }
      
  }

  submit(): void {
    const result = this.form.getRawValue();

    if (this.data.dialogType == InputDialogType.Create) {
      this.widgetService.addNewTheme(result);
    }
    
    if (this.data.dialogType == InputDialogType.Edit) {
      this.widgetService.updateTheme(result);
    }

    this.dialogRef.close();
  }
}
