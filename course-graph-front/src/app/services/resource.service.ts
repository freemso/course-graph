import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class ResourceService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //GET
    listResourcesOfNode() {
        let url = '/nodes/{nid}/resources';
    }

    //POST name, description
    addResourceToNode() {
        let url = '/nodes/{nid}/resources';
    }
    //GET
    getResourceData() {
        let url = '/resources/{rid}';
    }

    //PUT name, description
    updateResourceData() {
        let url = '/resources/{rid}';
    }

    //DELETE
    delete() {
        let url = '/resources/{rid}';
    }
}