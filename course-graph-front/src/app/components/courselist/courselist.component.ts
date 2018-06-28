import {Component, OnInit, TemplateRef} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {StorageService} from '../../services/storage.service';
import {MyHttpService} from '../../services/MyHttp.service';
import {CourseService} from '../../services/course.service';
import {AlertService} from '../../services/alert.service';

@Component({
    selector: 'app-courselist',
    templateUrl: './courselist.component.html',
    styleUrls: ['./courselist.component.css'],
})

export class CourselistComponent implements OnInit {
    modalRef: BsModalRef;
    chooseCourseWindowRef: BsModalRef;
    curUser;

    myCourses = [];

    choosableCourses = [];

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
        private courseService: CourseService,
        private storage: StorageService,
        private modalService: BsModalService,
        private alertService: AlertService
    ) {
    }

    ngOnInit() {
        this.curUser = this.storage.getItem("curUser");
        this.getMyCourses();
    }

    openAddCourseWindow(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    openChooseCourseWindow(template: TemplateRef<any>) {
        this.chooseCourseWindowRef = this.modalService.show(template);
        this.getChoosableCourses();
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
        this.modalRef.hide();
        console.log("begin to choose course:");

        // let url = "/courses/" + this.choosenCourse.id + "/students";
        let body = {"code": this.choosenCourse.code};

        let _that = this;
        this.courseService.addStudentToCourse(this.choosenCourse.id, body).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("choose course resp:");
            console.log(sucResp);

            _that.getMyCourses();
            _that.getChoosableCourses();

        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });

        this.choosenCourse = {
            "id": -1,
            "code": ""
        };
    }

    cancelAddCourse() {
        this.modalRef.hide();
        this.newCourse = {
            "name": "",
            "code": "",
        };
    }

    confirmAddCourse() {
        console.log("add new course:");
        console.log(this.newCourse);

        let _that = this;
        this.courseService.create(this.newCourse).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("add course resp:");
            console.log(sucResp);

            _that.getMyCourses();
            _that.modalRef.hide();
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.dir(errResp);
            alert(errResp.message);
        });

        this.newCourse = {
            "name": "",
            "code": "",
        };
    }

    getMyCourses() {
        console.log("get my courses:");

        let _that = this;
        this.courseService.listCoursesOfUser().subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("get courses resp:");
            console.log(sucResp);
            _that.myCourses = sucResp;
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.dir(errResp);
            alert(errResp.message);
        });
    }

    getChoosableCourses() {
        console.log("get choosable courses:");

        let _that = this;
        this.courseService.getChoosableCourses().subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("get choosable courses resp:");
            console.log(sucResp);
            _that.choosableCourses = sucResp;
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.dir(errResp);
            alert(errResp.message);
        });
    }

}