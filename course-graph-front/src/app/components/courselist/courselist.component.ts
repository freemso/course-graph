import { Component, OnInit, TemplateRef } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { StorageService } from '../../services/storage.service';
import { MyHttpService } from '../../services/MyHttp.service';
import { element } from 'protractor';



@Component({
  selector: 'app-courselist',
  templateUrl: './courselist.component.html',
  styleUrls: ['./courselist.component.css']
})
export class CourselistComponent implements OnInit {
  modalRef: BsModalRef;
  curUser;

  myCourses = [];

  choosableCourses = [
    {
    "name": "操作系统",
    "id": 6,
    "code": "03",
    "teacher_name": "a wei",
    "teacher_id ": "17",
    "student_num": 23
    }
  ];

  newCourse = {
    "name": "",
    "code": "",
  };

  choosenCourse = {
    "id": -1,
    "code": ""
  }

  constructor(
    private myHttp: MyHttpService,
    private storage: StorageService,
    private modalService: BsModalService,
  ) {
  }

  ngOnInit() {
    this.curUser = this.storage.getItem("curUser");
    console.log("current user: ");
    console.log(this.curUser);
    this.getCourses();
    console.log("all my courses:");
    console.log(this.myCourses);
  }

  openAddCourseWindow(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  openChooseCourseWindow(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  openInputCourseCodeWindow(template: TemplateRef<any>, id) {
    this.modalRef = this.modalService.show(template);
    this.setChoosenCourseId(id);
  }

  setChoosenCourseId(id) {
    this.choosenCourse.id = id;
  }

  setChoosenCourseCode(code) {
    this.choosenCourse.code = code;
  }

  //发送选课请求，更新myCourses
  chooseCourse() {
    console.log("begin to choose course:");

    let url="/courses/"+this.choosenCourse.id+"/students";
    let body = JSON.stringify({"code":this.choosenCourse.code});

    let _that = this;
    this.myHttp.post(url, body).subscribe(function (data) {
      console.log("choose course resp:");
      console.log(data);
      console.log(data['_body']);
      //更新课程列表
      _that.getCourses();
      _that.getChoosableCourses();

    }, function (err) {
      console.dir(err);
    });

    this.choosenCourse = {
      "id": -1,
      "code": ""
    };
  }

  cancelAddCourse() {
    this.modalRef.hide();
  }

  //发送添加请求，更新myCourses
  confirmAddCourse() {
    //发送请求
    console.log("begin to add new course:");
    console.log(this.newCourse);

    let url = "/courses";
    let body = JSON.stringify(this.newCourse);

    let _that = this;
    this.myHttp.post(url, body).subscribe(function (data) {
      console.log("add course resp:");
      console.log(data);
      console.log(data['_body']);
      //更新课程列表
      _that.getCourses();
      _that.modalRef.hide();
    }, function (err) {
      console.dir(err);
    });

    this.newCourse = {
      "name": "",
      "code": "",
    };
  }

  getCourses() {
    console.log("begin to get courses:");
    
    let url = "/account/courses";
    let body = JSON.stringify(this.newCourse);
    
    let _that = this;
    this.myHttp.get(url).subscribe(function (data) {
      console.log("get courses resp:");
      console.log(data);
      console.log(data['_body']);
      _that.myCourses = JSON.parse(data['_body']);
    }, function (err) {
      console.dir(err);
    });
  }

  getChoosableCourses(){
    console.log("begin to get choosable courses:");
    
    let url = "/account/courses";
    let body = JSON.stringify(this.newCourse);
    
    let _that = this;
    this.myHttp.get(url).subscribe(function (data) {
      console.log("get choosable courses resp:");
      console.log(data);
      console.log(data['_body']);
      _that.choosableCourses = JSON.parse(data['_body']);
    }, function (err) {
      console.dir(err);
    });
  }

}