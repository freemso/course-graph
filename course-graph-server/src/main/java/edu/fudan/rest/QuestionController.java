package edu.fudan.rest;


import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.AnswerQuestionReq;
import edu.fudan.dto.response.QuestionResp;
import edu.fudan.model.QuestionService;
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
        return new ResponseEntity(null, HttpStatus.CREATED);
    }
}
