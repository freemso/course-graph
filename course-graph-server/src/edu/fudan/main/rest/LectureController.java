package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.LectureResp;
import edu.fudan.main.model.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/lectures/{lid}")
public class LectureController {

    private NodeService nodeService;

    @Autowired
    public LectureController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping
    @Authorization
    ResponseEntity<LectureResp> getLectureMeta(@CurrentUser User currentUser, @PathVariable long lid) {
        return new ResponseEntity<>(nodeService.getLectureMeta(currentUser, lid), HttpStatus.OK);
    }

    @DeleteMapping
    @Authorization
    ResponseEntity deleteLecture(@CurrentUser User currentUser, @PathVariable long lid) {
        nodeService.deleteLecture(currentUser, lid);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/download")
    @Authorization
    public ResponseEntity<InputStreamResource> downloadLecture(@CurrentUser User currentUser, @PathVariable long lid) {
        LectureResp lectureResp = nodeService.getLectureMeta(currentUser, lid);
        // Set header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + lectureResp.getTitle() + "\"");
        return new ResponseEntity<>(nodeService.downloadLecture(currentUser, lid), headers, HttpStatus.OK);
    }
}
