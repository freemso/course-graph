import { StorageService } from './storage.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import {
    Http,
    RequestOptionsArgs,
    RequestOptions,
    Response,
    Headers
} from '@angular/http';

const baseURL = 'http://127.0.0.1:8080';

@Injectable()
export class MyHttpService {

    constructor(
        private http: Http,
        private storage:StorageService
    ) {
    }

    mergeToken = () => {
        let newHeaders = new Headers({"Content-Type": "application/json"});
        let token:any = this.storage.getItem("token");
        if (token) {
            console.log("token: " + token);
            newHeaders.set("Authorization", token);
        }
        return newHeaders;
    };

    get(url: string): Observable<Response> {
        return this.http.get(baseURL+url, {headers: this.mergeToken()});
    }

    post(url: string, body: any): Observable<Response> {
        return this.http.post(baseURL+url, body, {headers: this.mergeToken()});
    }

    put(url: string, body: any): Observable<Response> {
        return this.http.put(baseURL+url, body, {headers: this.mergeToken()});
    }

    delete(url: string): Observable<Response> {
        return this.http.delete(baseURL+url, {headers: this.mergeToken()});
    }

    patch(url: string, body: any): Observable<Response> {
        return this.http.patch(baseURL+url, body, {headers: this.mergeToken()});
    }

    head(url: string): Observable<Response> {
        return this.http.head(baseURL+url, {headers: this.mergeToken()});
    }

}
