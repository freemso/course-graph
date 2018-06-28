import {Injectable} from '@angular/core';
import {MyHttpService} from './MyHttp.service';


@Injectable()
export class AuthenticationService {
    constructor(private myHttp: MyHttpService) {
    }

    //POST email, password
    login(user) {
        let url = "/token";
        let body = JSON.stringify(user);

        return this.myHttp.post(url, body);
    }

    //DELETE delete a token
    logout() {
        let url = "/token";

        return this.myHttp.delete(url);
    }

}