import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class AnswerService {
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {
    }

    //GET
    listAnswersOfQuestion() {
        let url = '/questions/{qid}/answers';
    }

    //POST name, description
    addAnswerToQuestion() {
        let url = '/questions/{qid}/answers';
    }

    //GET
    getAnswerData() {
        let url = '/answers/{aid}';
    }

    //PUT name, description
    updateAnswerData() {
        let url = '/answers/{aid}';
    }

    //DELETE
    delete() {
        let url = '/answers/{aid}';
    }
}