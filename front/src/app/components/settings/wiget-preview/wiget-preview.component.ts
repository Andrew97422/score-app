import { Component } from '@angular/core';
import { LendingType, LendingTypeExt } from 'src/app/shared/models/lending-type';

@Component({
  selector: 'wiget-preview',
  templateUrl: './wiget-preview.component.html',
  styleUrls: ['./wiget-preview.component.css']
})
export class WigetPreviewComponent {
  LendingTypeExt=LendingTypeExt;
  LendingType=LendingType;
}
