export enum LendingType {
    MORTGAGE = 'MORTGAGE',
    CONSUMER = 'CONSUMER',
    AUTO_LOAN = 'AUTO_LOAN'
}

export class LendingTypeExt {
    static getName(v: LendingType): string {
        switch(v) {
            case LendingType.MORTGAGE:
                return 'Ипотека'
            case LendingType.CONSUMER:
                return 'Потребительский кредит'
            case LendingType.AUTO_LOAN:
                return 'Автокредит'
            default:
                return ''
        }
    }

    static getAll(): LendingType[] {
        return [
            LendingType.CONSUMER, LendingType.AUTO_LOAN, LendingType.MORTGAGE
        ];
    }
}
