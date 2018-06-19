package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.config.MvcConfig;
import edu.fudan.main.domain.Lecture;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.AddResourceReq;
import edu.fudan.main.dto.request.CreateQuestionReq;
import edu.fudan.main.dto.response.LectureResp;
import edu.fudan.main.dto.response.QuestionResp;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.model.NodeService;
import edu.fudan.main.model.QuestionService;
import jdk.management.resource.ResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    ResponseEntity<ResourceResp> addResourceToNode(@PathVariable String nid,
                                                   @CurrentUser User currentUser,
                                                   @RequestBody AddResourceReq resourceRequest) {
        return new ResponseEntity<ResourceResp>(nodeService.addUrlResourceToNode(currentUser, nid,
                resourceRequest.getLink(),resourceRequest.getTitle()), HttpStatus.OK);
    }

    @PostMapping("/resources/files")
    @Authorization
    public ResponseEntity<ResourceResp> addResourceToNode(@PathVariable String nid,
                                                          @CurrentUser User currentUser,
                                                          @RequestParam("file") MultipartFile files,
                                                          @RequestParam("description") String description) {

        return new ResponseEntity<ResourceResp>(nodeService.addFileResourcesToNode(currentUser, nid, files, description),
                HttpStatus.OK);


    }


    @PostMapping("/questions")
    @Authorization
    ResponseEntity<QuestionResp> addQuestionToNode(@PathVariable String nid, @CurrentUser User currentUser,
                                                   @RequestBody CreateQuestionReq createQuestionReq) {
        return new ResponseEntity<QuestionResp>(
                questionService.createQuestion(currentUser, nid, createQuestionReq.getDescription(),
                        createQuestionReq.getType(), createQuestionReq.getChoices(), createQuestionReq.getAnswer())
                , HttpStatus.OK);
    }


    @PostMapping("/lectures")
    @Authorization
    public ResponseEntity<LectureResp> addLectureToNode(@PathVariable String nid,
                                                        @CurrentUser User currentUser,
                                                        @RequestParam("lecture") MultipartFile files,
                                                        @RequestParam("description") String description) {

        return new ResponseEntity<LectureResp>(nodeService.addNewLectureToNode(currentUser, nid, files, description),
                HttpStatus.OK);


    }


}
