import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class CourseService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //POST name, code, teacher_id
    create(newCourse) {
        let url = "http://127.0.0.1:8080/courses";

        let body = JSON.stringify(newCourse);

        this.http.post(url, body, { headers: this.headers }).subscribe(function (data) {
            console.dir(data);
            console.log(data['_body']);

        }, function (err) {
            console.dir(err);
        });
    }

    //DELETE
    delete() {
        let url = '/course/{cid}';
    }

    //GET
    listCoursesOfUser() {
        let url = 'http://127.0.0.1:8080/acount/courses';

    }

    //GET
    getCourseData() {
        let url = '/courses/{uid}';
    }

    //PUT name, code
    update() {
        let url = '/courses/{cid}';
    }

    //GET
    listStudentsOfCourse() {
        let url = '/courses/{cid}/students';
    }

    //POST uid
    addStudentToCourse() {
        let url = '/courses/{cid}/students';
    }
}
