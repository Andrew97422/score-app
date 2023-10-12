export class CommonProduct {
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
}

export class AutoLoanProduct extends CommonProduct {
    mileage: string;
}

export class ConsumerProduct extends CommonProduct {
    discount: string;
}
