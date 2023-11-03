export class Widget {
    id: number;
    interestRate: number;
    color: string;
    font: string;
    name: string;
    themeId: number;

    constructor(init: Partial<Widget>) {
        Object.assign(this, init);
    }
}
