import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { WidgetTheme, WidgetThemeExt } from '../../shared/models/widget-theme';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { WidgetService } from '../../shared/services/widget-service';
import { Widget } from '../../shared/models/widget';
import { MatDialog } from '@angular/material/dialog';
import { WidgetInputComponent } from './widget-input/widget-input.component';
import { InputDialogModel } from 'src/app/shared/models/input-dialog-type';

@Component({
  selector: 'settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
  LendingType=LendingType;
  LendingTypeExt=LendingTypeExt;
  WidgetThemeExt=WidgetThemeExt;

  theme = new FormControl(WidgetTheme.Standard);
  widgets: Widget[] = []; 

  constructor(
    private dialog: MatDialog,
    private widgetService: WidgetService) {
      this.load();
  }

  createWidget(): void {
    this.dialog.open(WidgetInputComponent, { 
      data: new InputDialogModel<any> ({
        title: 'Создание темы виджета',
        applyButton: 'Применить'
      })
    });
  }

  private load(): void {
    this.widgetService.getWidgets().subscribe(x => this.widgets = x as Widget[]);
  }
}
