import { Injectable } from '@angular/core';
import { Http, Jsonp, Headers } from '@angular/http';
// import { Observable } from 'rxjs';
// import 'rxjs/Rx';

@Injectable()
export class QuestionService {
    private headers = new Headers({ 'Content-Type': 'application/json' });

    constructor(private http: Http) { }

    //GET
    listQuestionsOfNode() {
        let url = '/nodes/{nid}/questions';
    }

    //POST name, description
    addQuestionToNode() {
        let url = '/nodes/{nid}/questions';
    }

    //GET
    getQuestionData() {
        let url = '/questions/{qid}';
    }

    //PUT name, description
    updateQuestionData() {
        let url = '/questions/{qid}';
    }

    //DELETE
    delete() {
        let url = '/questions/{qid}';
    }
}