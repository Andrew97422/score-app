export enum LoanCollateralType {
    APARTMENT,
    HOUSE,
    LAND,
    CAR,
    WITHOUT_COLLATERAL
}

export class LoanCollateralTypeExt {
    static getName(v: LoanCollateralType): string {
        switch(v) {
            case LoanCollateralType.APARTMENT:
                return 'Квартира'
            case LoanCollateralType.HOUSE:
                return 'Дом'
            case LoanCollateralType.LAND:
                return 'Земля'
            case LoanCollateralType.CAR:
                return 'Машина'
            case LoanCollateralType.WITHOUT_COLLATERAL:
                return 'Без обеспечения'
            default:
                return ''
        }
    }

    static getAll(): LoanCollateralType[] {
        return Object
        .keys(LoanCollateralType)
        .map(x => +x as LoanCollateralType)
        .filter(x => !Number.isNaN(x) && x >= 0)
    }
}
