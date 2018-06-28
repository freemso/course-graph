import {Injectable} from '@angular/core';
import {MyHttpService} from './MyHttp.service';


@Injectable()
export class ResourceService {

    constructor(private myHttp: MyHttpService) {
    }

    //GET
    listResourcesOfNode(nid) {
        let url = "/nodes/" + nid + "/resources";

        return this.myHttp.get(url);
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
    delete(rid) {
        let url = "/resources/" + rid;

        return this.myHttp.delete(url);
    }
}