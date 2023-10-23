import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';
import { WidgetTheme, WidgetThemeExt } from '../../shared/models/widget-theme';
import { FormControl } from '@angular/forms';

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

  constructor() {

  }
}
