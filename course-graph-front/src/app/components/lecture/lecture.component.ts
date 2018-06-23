import { Component, OnInit, Input } from '@angular/core';
import { StorageService } from '../../services/storage.service'
import { LectureService } from '../../services/lecture.service';

import 'rxjs/Rx';
import { MyHttpService } from '../../services/MyHttp.service';


@Component({
  selector: 'app-lecture',
  templateUrl: './lecture.component.html',
  styleUrls: ['./lecture.component.css']
})
export class LectureComponent implements OnInit {
  @Input() curNodeId;
  curUser = this.storage.getItem("curUser");
  nid;
  token = this.storage.getItem('token');

  lectures = [];


  constructor(
    private storage: StorageService,
    private myHttp: MyHttpService,
    private lectureService: LectureService) {
  }

  ngOnInit() {
  }

  getLectures(nid) {
    this.nid = nid;
    console.log("get lectures");

    let _that = this;
    this.lectureService.listLecturesOfNode(nid).subscribe(function (suc) {
      let sucResp = JSON.parse(suc['_body']);
      console.log("get lectures resp");
      console.log(sucResp);
      _that.lectures = sucResp;
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert(errResp);
    });
    console.log(this.lectures);
  }

  deleteLecture(lid) {
    console.log("delete lecture");

    let _that = this;
    this.lectureService.delete(lid).subscribe(function (suc) {
      console.log("delete lecture resp");
      console.log(suc);
      _that.getLectures(_that.nid);
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert(errResp);
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
