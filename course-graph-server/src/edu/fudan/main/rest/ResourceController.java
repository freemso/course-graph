package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.Resource;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.model.NodeService;
import edu.fudan.main.model.QuestionService;
import org.neo4j.ogm.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/resources/{rid}")
public class ResourceController {

    private final NodeService nodeService;


    @Autowired
    public ResourceController(NodeService nodeService) {
        this.nodeService = nodeService;
    }


    @GetMapping(value = "/file")
    public ResponseEntity<InputStreamResource> downloadFileResource(@PathVariable long resourceId) {
        try {
            Resource resource = nodeService.getResource(resourceId);
            //set header
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + resource.getOriginalFileName() + "\"");
            return new ResponseEntity<InputStreamResource>(
                    new InputStreamResource(new FileInputStream(nodeService.downloadFile(resourceId))),
                    headers,
                    HttpStatus.OK);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<InputStreamResource>((InputStreamResource) null, HttpStatus.NOT_FOUND);
        }
    }


}
