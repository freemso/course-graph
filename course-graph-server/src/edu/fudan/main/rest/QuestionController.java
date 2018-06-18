package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.AnswerQuestionReq;
import edu.fudan.main.dto.response.QuestionResp;
import edu.fudan.main.model.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/questions/{qid}")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    @Authorization
    ResponseEntity<QuestionResp> getQuestion(@CurrentUser User currentUser, @PathVariable long qid) {
        return new ResponseEntity<>(questionService.getQuestion(currentUser, qid), HttpStatus.OK);
    }

    @DeleteMapping
    @Authorization
    ResponseEntity deleteQuestion(@CurrentUser User currentUser, @PathVariable long qid) {
        questionService.deleteQuestion(currentUser, qid);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/answers")
    @Authorization
    ResponseEntity postAnswer(@CurrentUser User currentUser, @PathVariable long qid,
                              @RequestBody AnswerQuestionReq answerQuestionReq) {
        questionService.createAnswerEntry(currentUser, qid, answerQuestionReq.getAnswer());
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
