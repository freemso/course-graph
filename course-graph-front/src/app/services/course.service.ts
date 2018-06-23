import { Injectable } from '@angular/core';
import { MyHttpService } from './MyHttp.service';


@Injectable()
export class CourseService {

    constructor(private myHttp: MyHttpService) { }

    //POST name, code, teacher_id
    create(newCourse) {
        let url = "/courses";
        let body = JSON.stringify(newCourse);

        return this.myHttp.post(url, body);
    }

    //DELETE
    delete() {
        let url = "/course/{cid}";
    }

    //GET
    getChoosableCourses() {
        let url = "/courses";

        return this.myHttp.get(url);
    }

    //GET 
    listCoursesOfUser() {
        let url = "/account/courses";

        return this.myHttp.get(url);
    }

    //GET
    getCourseData() {
        let url = "/courses/{uid}";
    }

    //PUT name, code
    update() {
        let url = "/courses/{cid}";
    }

    //GET
    listStudentsOfCourse() {
        let url = "/courses/{cid}/students";
    }

    //POST uid
    addStudentToCourse(cid, code) {
        let url = "/courses/"+cid+"/students";
        let body = JSON.stringify(code);

        return this.myHttp.post(url, body);
    }
}