export enum CountActiveLoans {
    NO_CREDITS='NO_CREDITS',
    FROM_ONE_TO_TWO='FROM_ONE_TO_TWO',
    FROM_THREE_TO_FIVE='FROM_THREE_TO_FIVE',
    MORE_THAN_FIVE='MORE_THAN_FIVE'
}

export class CountActiveLoansExt {
    static getName(v: CountActiveLoans): string {
        switch(v) {
            case CountActiveLoans.NO_CREDITS:
                return 'Нет кредитов'
            case CountActiveLoans.FROM_ONE_TO_TWO:
                return 'От 1 до 2'
            case CountActiveLoans.FROM_THREE_TO_FIVE:
                return 'От 3 до 5'
            case CountActiveLoans.MORE_THAN_FIVE:
                return 'Больше 5'
            default:
                return ''
        }
    }

    static getAll(): CountActiveLoans[] {
        return Object
        .keys(CountActiveLoans)
        .map(x => x as CountActiveLoans)
        .filter(x => !Number.isNaN(x))
    }
}
