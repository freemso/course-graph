import { StorageService } from './storage.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import {
    Http,
    Response,
    Headers
} from '@angular/http';

@Injectable()
export class MyHttpService {

    baseURL = 'http://0.0.0.0:7477';

    constructor(
        private http: Http,
        private storage: StorageService
    ) {
    }

    mergeToken = () => {
        let newHeaders = new Headers({ "Content-Type": "application/json" });
        let token: any = this.storage.getItem("token");
        if (token) {
            console.log("token: " + token);
            newHeaders.set("Authorization", token);
        }
        return newHeaders;
    };

    get(url: string): Observable<Response> {
        return this.http.get(this.baseURL + url, { headers: this.mergeToken() });
    }

    post(url: string, body: any): Observable<Response> {
        return this.http.post(this.baseURL + url, body, { headers: this.mergeToken() });
    }

    put(url: string, body: any): Observable<Response> {
        return this.http.put(this.baseURL + url, body, { headers: this.mergeToken() });
    }

    delete(url: string): Observable<Response> {
        return this.http.delete(this.baseURL + url, { headers: this.mergeToken() });
    }

    patch(url: string, body: any): Observable<Response> {
        return this.http.patch(this.baseURL + url, body, { headers: this.mergeToken() });
    }

    head(url: string): Observable<Response> {
        return this.http.head(this.baseURL + url, { headers: this.mergeToken() });
    }

}