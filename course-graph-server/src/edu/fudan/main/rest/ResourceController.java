package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.Resource;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.ResourceResp;
import edu.fudan.main.model.NodeService;
import edu.fudan.main.model.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    @Authorization
    ResponseEntity<ResourceResp> getResourceMeta(@CurrentUser User currentUser, @PathVariable long rid) {
        return new ResponseEntity<>(nodeService.getResourceMeta(rid), HttpStatus.OK);
    }

    @DeleteMapping
    @Authorization
    ResponseEntity deleteResource(@CurrentUser User currentUser, @PathVariable long rid) {
        nodeService.deleteResource(currentUser, rid);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/file")
    @Authorization
    ResponseEntity<MultipartFile> getResourceFile(@CurrentUser User currentUser, @PathVariable long rid) {
        // TODO
        return null;
    }

    /* method added by zzxiong
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
     */
}
