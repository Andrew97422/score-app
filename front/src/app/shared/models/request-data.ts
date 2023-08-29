export class RequestData {
    email: string;
    workExperience: string;
    loanCollateralType: string;
    countActiveLoans: string;
    currentDebtLoad: number;
    monthlyIncome: string;
    amount: number;
    term: number;
    minRate: number;
    maxRate: number;
    military: boolean;
    stateEmployee: boolean;
    psbClient: boolean;
    farEastInhabitant: boolean;
    newSubjectsResident: boolean;
    itSpecialist: boolean;
    birthday: string;

    constructor(init: Partial<RequestData>) {
        Object.assign(this, init);
    }
}
