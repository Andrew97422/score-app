export class ConfirmData {
    title: string;
    desription: string;
    buttonName: string;

    constructor(init: Partial<ConfirmData>) {
        Object.assign(this, init);
    }
}