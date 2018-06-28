import {Component, Input, OnInit} from '@angular/core';
import {StorageService} from '../../services/storage.service'
import {MyHttpService} from '../../services/MyHttp.service';
import {ResourceService} from '../../services/resource.service';

@Component({
    selector: 'app-resource',
    templateUrl: './resource.component.html',
    styleUrls: ['./resource.component.css']
})
export class ResourceComponent implements OnInit {
    @Input() curNodeId;
    curUser = this.storage.getItem("curUser");
    nid;
    token = this.storage.getItem('token');


    resources = [];

    constructor(
        private storage: StorageService,
        private myHttp: MyHttpService,
        private resourceService: ResourceService
    ) {
    }

    ngOnInit() {
    }

    getResources(nid) {
        this.nid = nid;
        console.log("get resources");

        let _that = this;
        this.resourceService.listResourcesOfNode(this.nid).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("get resources resp:");
            console.log(sucResp);
            _that.resources = sucResp;
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp);
        });
    }

    deleteResource(rid) {
        console.log("delete resource");

        let _that = this;
        this.resourceService.delete(rid).subscribe(function (suc) {
            console.log("delete resource resp:");
            console.log(suc);
            _that.getResources(_that.nid);
        }, function (err) {
            console.log(err);
            alert(err);
        });
    }

    onSuc(e) {
        alert("success");
        console.log(e);
    }

    onErr(e) {
        console.log(this.curNodeId);
        alert("fail");
        console.log(e);
    }
}