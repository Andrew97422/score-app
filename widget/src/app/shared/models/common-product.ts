import { LendingType } from './lending-type';

export class CommonProduct {
    id: number;
    name: string;
    minAmount: number;
    maxAmount: number;
    minTerm: number;
    maxTerm: number;
    minRate: number;
    url: string;
    comment: string;
    startDate: string;
    finishDate: string;
    active: boolean;

    lendingType: LendingType;
}

export class AutoLoanProduct extends CommonProduct {
    mileage: string;
}

export class ConsumerProduct extends CommonProduct {
    discount: string;
}

export class MortgageProduct extends CommonProduct {
    addToInterest: string;
    downPayment: string;
}
