import {Injectable} from '@angular/core';
import {MyHttpService} from './MyHttp.service';

@Injectable()
export class UserService {

    constructor(private myHttp: MyHttpService) {
    }

    //POST email, name, password, type
    register(user) {
        let url = '/users';
        let body = JSON.stringify(user);
        return this.myHttp.post(url, body);
    }

    //DELETE 
    delete() {
        let url = "/account";

        return this.myHttp.delete(url);
    }

    //PUT email, name, password, newPassword
    update(user) {
        let url = '/account';
        let body = JSON.stringify(user);

        return this.myHttp.put(url, body);
    }

    //GET
    getPrivate() {
        let url = "/account";

        return this.myHttp.get(url);
    }

    //GET
    getPublic() {
        let url = '/users/{uid}';
    }

    //POST
    getVerificationCode(email) {
        let url = "/users/verification_code";
        let body = JSON.stringify(email);

        return this.myHttp.post(url, body);
    }
}