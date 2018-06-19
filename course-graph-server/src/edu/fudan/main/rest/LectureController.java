package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.Lecture;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.LectureResp;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.exception.LectureIOException;
import edu.fudan.main.model.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@CrossOrigin
@RequestMapping("/lectures/{lid}")
public class LectureController {

    @Autowired
    private NodeService nodeService;

    @GetMapping
    @Authorization
    ResponseEntity<LectureResp> getLectureMeta(@CurrentUser User currentUser, @PathVariable long lid) {
        return new ResponseEntity<LectureResp>(nodeService.getLectureMeta(currentUser, lid), HttpStatus.OK);
    }



    @DeleteMapping
    @Authorization
    ResponseEntity deleteLecture(@CurrentUser User currentUser, @PathVariable long lid) {
        nodeService.deleteLecture(currentUser, lid);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/download")
    @Authorization
    public ResponseEntity<InputStreamResource> downloadLecture(@CurrentUser User user, @PathVariable long lid) {
        try {
            LectureResp lectureResp = nodeService.getLectureMeta(user, lid);
            //set header
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + lectureResp.getTitle() + "\"");
            return new ResponseEntity<InputStreamResource>(
                    new InputStreamResource(new FileInputStream(nodeService.downloadLecture(lid))),
                    headers,
                    HttpStatus.OK);
        } catch (FileNotFoundException e) {
            throw new LectureIOException(e.getMessage());
//            return new ResponseEntity<InputStreamResource>((InputStreamResource) null, HttpStatus.NOT_FOUND);
        }
    }
}
