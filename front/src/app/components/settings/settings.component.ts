import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { WidgetTheme, WidgetThemeExt } from '../../shared/models/widget-theme';
import { FormControl } from '@angular/forms';
import { WidgetService } from '../../shared/services/widget-service';
import { Widget } from '../../shared/models/widget';
import { MatDialog } from '@angular/material/dialog';
import { WidgetInputComponent } from './widget-input/widget-input.component';
import { InputDialogModel, InputDialogType } from 'src/app/shared/models/input-dialog-type';
import { WigetPreviewComponent } from './wiget-preview/wiget-preview.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ConfirmData } from '../confirm-dialog/confirm-data.model';

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
  widget: Object;

  constructor(
    private dialog: MatDialog,
    private widgetService: WidgetService) {
      this.widgetService.getWidget().subscribe(x => this.widget = x);
      this.load();
  }

  async createWidget(): Promise<void> {
    await this.dialog.open(WidgetInputComponent, { 
      data: new InputDialogModel<any> ({
        dialogType: InputDialogType.Create,
        title: 'Создание темы виджета',
        applyButton: 'Сохранить'
      })
    }).afterClosed().toPromise();

    this.load();
  }

  async edit(w): Promise<void> {
    await this.dialog.open(WidgetInputComponent, { 
      data: new InputDialogModel<any> ({
        dialogType: InputDialogType.Edit,
        title: 'Редактирование темы виджета',
        applyButton: 'Сохранить',
        data: w
      })
    }).afterClosed().toPromise();

    this.load();
  }

  select(id: number): void {
    this.widgetService.setWidget(new Widget({ themeId: id, interestRate: 8.4 }));
  }

  async preview(widget: Widget): Promise<void> {
    document.documentElement.style.setProperty('--widget-theme-color', widget.color);
    document.documentElement.style.setProperty('--widget-font-family', widget.font);
    await this.dialog.open(WigetPreviewComponent).afterClosed().toPromise();
  }

  async deleteTheme(widget: Widget): Promise<void> {
    const answer = await this.dialog.open(ConfirmDialogComponent, {data: new ConfirmData({
      title: 'Удаление',
      buttonName: 'Удалить',
      desription: 'Удалить тему ' + widget.name + '?'
    })}).afterClosed().toPromise();
    
    if (answer == true) {
      this.widgetService.deleteTheme(widget.id);
      this.load();
    }
  }

  private load(): void {
    this.widgetService.getWidgets().subscribe(x => this.widgets = x as Widget[]);
  }
}
