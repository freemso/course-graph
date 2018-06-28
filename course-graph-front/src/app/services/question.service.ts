import {Injectable} from '@angular/core';
import {MyHttpService} from './MyHttp.service';


@Injectable()
export class QuestionService {

    constructor(private myHttp: MyHttpService) {
    }

    //GET
    listQuestionsOfNode(nid) {
        let url = "/nodes/" + nid + "/questions";

        return this.myHttp.get(url);
    }

    //POST name, description
    addQuestionToNode(nid, newQuestion) {
        let url = "/nodes/" + nid + "/questions";
        let body = JSON.stringify(newQuestion);

        return this.myHttp.post(url, body);
    }

    //POST name, description
    addAnswerToQuestion(qid, answer) {
        let url = "/questions/" + qid + "/answers";
        let body = JSON.stringify(answer);

        return this.myHttp.post(url, body);
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
    delete(qid) {
        let url = "/questions/" + qid;

        return this.myHttp.delete(url);
    }
}