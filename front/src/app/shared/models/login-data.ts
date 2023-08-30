export class LoginData {
    login: string;
    password: string;
   
    constructor(init: Partial<LoginData>) {
        Object.assign(this, init);
    }
}