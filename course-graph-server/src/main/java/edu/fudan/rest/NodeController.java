package edu.fudan.rest;

import edu.fudan.dto.request.AddUrlResourceReq;
import edu.fudan.dto.request.CreateQuestionReq;
import edu.fudan.dto.response.ResourceResp;
import edu.fudan.model.QuestionService;
import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.response.LectureResp;
import edu.fudan.dto.response.QuestionResp;
import edu.fudan.model.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/nodes/{nid}")
public class NodeController {

    private final NodeService nodeService;

    private final QuestionService questionService;

    @Autowired
    public NodeController(NodeService nodeService, QuestionService questionService) {
        this.nodeService = nodeService;
        this.questionService = questionService;
    }

    @GetMapping("/resources")
    @Authorization
    ResponseEntity<List<ResourceResp>> getResourcesOfNode(@PathVariable String nid,
                                                          @CurrentUser User currentUser) {
        return new ResponseEntity<>(nodeService.getAllResourcesOfNode(currentUser, nid), HttpStatus.OK);
    }

    @GetMapping("/lectures")
    @Authorization
    ResponseEntity<List<LectureResp>> getLecturesOfNode(@PathVariable String nid,
                                                        @CurrentUser User currentUser) {
        return new ResponseEntity<>(nodeService.getAllLecturesOfNode(currentUser, nid), HttpStatus.OK);
    }

    @GetMapping("/questions")
    @Authorization
    ResponseEntity<List<QuestionResp>> getQuestionsOfNode(@PathVariable String nid,
                                                          @CurrentUser User currentUser) {
        return new ResponseEntity<>(questionService.getAllQuestionsOfNode(currentUser, nid), HttpStatus.OK);
    }

    @PostMapping("/resources/url")
    @Authorization
    ResponseEntity<ResourceResp> addUrlResourceToNode(@PathVariable String nid,
                                                       @CurrentUser User currentUser,
                                                       @RequestBody AddUrlResourceReq resourceRequest) {
        return new ResponseEntity<>(nodeService.addUrlResourceToNode(currentUser, nid,
                resourceRequest.getLink(), resourceRequest.getTitle()), HttpStatus.CREATED);
    }

    @PostMapping("/resources/files")
    @Authorization
    public ResponseEntity<ResourceResp> addFileResourceToNode(@PathVariable String nid,
                                                              @CurrentUser User currentUser,
                                                              @RequestParam("file") MultipartFile files) {
        return new ResponseEntity<>(nodeService.addFileResourcesToNode(currentUser, nid, files),
                HttpStatus.CREATED);
    }


    @PostMapping("/questions")
    @Authorization
    ResponseEntity<QuestionResp> addQuestionToNode(@PathVariable String nid,
                                                   @CurrentUser User currentUser,
                                                   @RequestBody CreateQuestionReq createQuestionReq) {
         return new ResponseEntity<>(questionService.createQuestion(
                        currentUser, nid, createQuestionReq.getDescription(),
                        createQuestionReq.getType(), createQuestionReq.getChoices(), createQuestionReq.getAnswer()
                ), HttpStatus.CREATED);
    }


    @PostMapping("/lectures")
    @Authorization
    public ResponseEntity<LectureResp> addLectureToNode(@PathVariable String nid,
                                                        @CurrentUser User currentUser,
                                                        @RequestParam("lecture") MultipartFile files) {
        return new ResponseEntity<>(nodeService.addLectureToNode(currentUser, nid, files), HttpStatus.CREATED);
    }


}
