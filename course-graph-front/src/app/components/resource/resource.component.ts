import { Component, OnInit, Input } from '@angular/core';
import { StorageService } from '../../services/storage.service'
import { MyHttpService } from '../../services/MyHttp.service';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css']
})
export class ResourceComponent implements OnInit {
  @Input() curNodeId;
  token = this.storage.getItem('token');


  resources = [];

  constructor(
    private storage: StorageService,
    private myHttp: MyHttpService
  ) { }

  ngOnInit() {
  }

  getResources() {
    console.log("get resources");

    let url = "/nodes/" + this.curNodeId + "/resources";

    let _that = this;
    this.myHttp.get(url).subscribe(function (data) {
      console.log("get resources resp:");
      console.log(data);
      console.log(data['_body']);
      _that.resources = JSON.parse(data['_body']);
    }, function (err) {
      console.log(err);
    });
  }

  onSuc(e) {
    alert("success");
    console.log(e);
  }

  onErr(e) {
    console.log(this.curNodeId);
    alert("failer");
    console.log(e);
  }
}