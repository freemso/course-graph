import { Component, OnInit, Input } from '@angular/core';
import { StorageService } from '../../services/storage.service'
import { MyHttpService } from '../../services/MyHttp.service';

import { Observable } from 'rxjs';
import 'rxjs/Rx';


@Component({
  selector: 'app-lecture',
  templateUrl: './lecture.component.html',
  styleUrls: ['./lecture.component.css']
})
export class LectureComponent implements OnInit {
  @Input() curNodeId;
  token = this.storage.getItem('token');

  lectures = [];


  constructor(
    private storage: StorageService,
    private myHttp: MyHttpService) {}

  ngOnInit() {
  }

  getLectures() {
    console.log("get lectures");

    let url = "/nodes/" + this.curNodeId + "/lectures";

    let _that = this;
    this.myHttp.get(url).subscribe(function (data) {
      console.log("get lectures resp");
      console.log(data);
      console.log(data['_body']);
      _that.lectures = JSON.parse(data['_body']);
    }, function (err) {
      console.dir(err);
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
