import { Injectable } from '@angular/core';
import { MyHttpService } from './MyHttp.service';


@Injectable()
export class LectureService {

    constructor(private myHttp: MyHttpService) { }

    //GET
    listLecturesOfNode(nid) {
        let url = "/nodes/" + nid + "/lectures";

        return this.myHttp.get(url);
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
    delete(lid) {
        let url = "/lectures/" + lid;

        return this.myHttp.delete(url);
    }
}