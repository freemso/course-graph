import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class GraphService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //GET
    listGraphsOfCourse() {
        let url = 'courses/{cid}/graphs';
    }

    //POST name, description
    addGraphToCourse() {
        let url = '/courses/{cid}/graphs';
    }

    //GET
    getGraphData() {
        let url = '/graph/{gid}';
    }

    //PUT name, description
    updateGraphData() {
        let url = '/graph/{gid}';
    }

    //DELETE
    delete() {
        let url = '/graphs/{gid}';
    }




}