import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class LectureService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //GET
    listLecturesOfNode() {
        let url = '/nodes/{nid}/lectures';
    }

    //POST name, description
    addLectureToNode() {
        let url = '/nodes/{nid}/lectures';
    }

    //GET
    getLectureData() {
        let url = '/lectures/{lid}';
    }

    //PUT name, description
    updateLectureData() {
        let url = '/lectures/{lid}';
    }

    //DELETE
    delete() {
        let url = '/lectures/{lid}';
    }
}