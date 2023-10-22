export enum InputDialogType {
    Create,
    Edit,
    View
}

export class InputDialogModel<T> {
    data: T;
    dialogType: InputDialogType;
    title: string;
    applyButton: string;

    constructor(init: Partial<InputDialogModel<T>>) {
        Object.assign(this, init);
    }
}
