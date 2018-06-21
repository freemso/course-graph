import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class NodeService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //GET
    listNodesOfGraph() {
        let url = '/graphs/{gid}/nodes';
    }

    //POST name, description
    addNodeToGraph() {
        let url = '/graphs/{gid}/nodes';
    }

    //GET
    getNodeData() {
        let url = '/nodes/{nid}';
    }

    //PUT name, description
    updateNodeData() {
        let url = '/nodes/{nid}';
    }

    //DELETE
    delete() {
        let url = '/nodes/{nid}';
    }
}