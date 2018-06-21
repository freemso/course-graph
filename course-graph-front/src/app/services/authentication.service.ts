import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class AuthenticationService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    respBody: any;
    constructor(private http: Http) { }

    //POST email, password
    login(user) {
        let url = "http://127.0.0.1:8080/token";
        let body = JSON.stringify(user);

        let _that = this;
        return this.http.post(url, body, { headers: this.headers }).subscribe(function (data) {
            console.dir(data);
            console.log("get token: ");
            console.log(JSON.parse(data['_body']));

            _that.respBody = JSON.parse(data['_body']);
            console.log(_that.respBody['token']);
            return _that.respBody;
        }, function (err) {
            console.dir(err);
        });

        // console.log("ssssss");
        // console.log(_that.respBody);
        // return _that.respBody['token'];
    }

    //DELETE delete a token
    logout() {
        let url = "/token";
        localStorage.removeItem('currentUser');
    }

    returnTest() {
        return {
            "hello": "world"
        };
    }
}
