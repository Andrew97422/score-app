import { Component } from '@angular/core';
import { StateService } from '../../../shared/services/state-service';
import { Router } from '@angular/router';
import { LendingType } from 'src/app/shared/models/lending-type';

@Component({
  selector: 'shop-prototype',
  templateUrl: './shop-prototype.component.html',
  styleUrls: ['./shop-prototype.component.css']
})
export class ShopPrototypeComponent {
  cars = [
    {
      imgUrl: 'https://60.img.avito.st/image/1/1.ZZrnN7a4yXPRgEt-oUpzlsuVy3VZlktl0ZvLcVeewXlR.8RJ4scVZkeB_CB6ZcNYwkbtI8XdUNM7GcGbrQCtcfTU',
      name: 'SsangYong Kyron 2.0 MT, 2010, 131 000 км',
      price: '799000',
      lendingType: LendingType.AUTO_LOAN
    },
    {
      imgUrl: 'https://40.img.avito.st/image/1/1.OEvHrra5lKLxGRavu-pRBwsNkqhzjYIgfg2WpnsHnqA.9lmnQt6xPES6pFd0O8SscRf75ovJiYeIT0xM2A6Vm0w',
      name: 'Chery Tiggo 4 Pro 1.5 CVT, 2023',
      price: '2329900',
      lendingType: LendingType.AUTO_LOAN
    },
    {
      imgUrl: 'https://20.img.avito.st/image/1/1.cVH4yba43bjOfl-1luwyIuhr375GaF-uzmXfukhg1bJO.4bOIgIgRSHrowdxXOva5BAUq_tNVj5-X3WSlPOv5Ptk',
      name: 'EXEED LX 1.6 AMT, 2023',
      price: '3230000',
      lendingType: LendingType.AUTO_LOAN
    }
  ];

  homes = [
    {
      imgUrl: 'https://80.img.avito.st/image/1/1.8HwSUra6XJUk5d6Yfh2JMdPxWp-mcUoXq_Fek7L3Xg.i6k65QVssh-7ORbpF1OrrcmODI_4ZW-Ua2RPp_jkmnA',
      name: 'Дом 92 м² на участке 6 сот.',
      price: '6440000',
      lendingType: LendingType.MORTGAGE
    },
    {
      imgUrl: 'https://90.img.avito.st/image/1/1.3bylRba5cVWT8vNYkzrcv7Pnc1Mb5PNDk-lzVxfwdVc.WLdTDCPpz-6qxB55d4cGffonKg7uJ3eQu7Ve1_nAzRg',
      name: 'Дом 95 м² на участке 7 сот.',
      price: '7653000',
      lendingType: LendingType.MORTGAGE
    },
    {
      imgUrl: 'https://60.img.avito.st/image/1/1.37wQjba5c1UmOvFYIvaBqSAvcVOuLPFDJiFxV6I4d1c.OXjizywTQax9nn9K2DsBeVtoFfmRD6k3-PDKuSH9igM',
      name: 'Таунхаус 115 м² на участке 6 сот.',
      price: '6700000',
      lendingType: LendingType.MORTGAGE
    }
  ];

  constructor(
    private stateService: StateService,
    private router: Router) {}

  viewProduct(product): void {
    this.stateService.product = product;
    this.router.navigate(['product']);
  }
}
