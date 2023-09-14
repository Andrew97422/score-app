export class LoginData {
    username: string;
    password: string;
   
    constructor(init: Partial<LoginData>) {
        Object.assign(this, init);
    }
}