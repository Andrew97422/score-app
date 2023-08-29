export class UserAuthData {
    login: string;
    password: string;
    lastName: string;
    firstName: string;
    surName: string;
    birthday: string;
    phone: string;
    email: string;

    constructor(init: Partial<UserAuthData>) {
        Object.assign(this, init);
    }
}