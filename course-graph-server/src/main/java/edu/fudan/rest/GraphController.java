package edu.fudan.rest;

import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.UpdateGraphMetaReq;
import edu.fudan.dto.request.UpdateJsmindReq;
import edu.fudan.dto.response.GraphMetaResp;
import edu.fudan.dto.response.JsmindResp;
import edu.fudan.model.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin
@RequestMapping("/graphs")
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/{gid}")
    @Authorization
    ResponseEntity<GraphMetaResp> getGraphMeta(@CurrentUser User currentUser,
                                               @PathVariable long gid) {
        return new ResponseEntity<>(graphService.getGraphMeta(currentUser, gid), HttpStatus.OK);
    }

    @PutMapping("/{gid}")
    @Authorization
    ResponseEntity<GraphMetaResp> updateGraphMeta(@PathVariable long gid,
                                                  @CurrentUser User currentUser,
                                                  @Valid @RequestBody UpdateGraphMetaReq updateGraphMetaReq) {
        return new ResponseEntity<>(graphService.updateGraphMeta(currentUser, gid,
                updateGraphMetaReq.getName(), updateGraphMetaReq.getDescription()), HttpStatus.OK);
    }

    @GetMapping("/{gid}/jsmind")
    @Authorization
    ResponseEntity<JsmindResp> getGraphJsmind(@CurrentUser User currentUser,
                                              @PathVariable long gid) {
        return new ResponseEntity<>(graphService.getJsmind(currentUser, gid), HttpStatus.OK);
    }

    @PutMapping("/{gid}/jsmind")
    @Authorization
    ResponseEntity<JsmindResp> updateGraphJsmind(@PathVariable long gid,
                                                 @CurrentUser User currentUser,
                                                 @Valid @RequestBody UpdateJsmindReq updateJsmindReq) {
        return new ResponseEntity<>(graphService.updateJsmind(currentUser, gid, updateJsmindReq.getJsmind()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{gid}")
    @Authorization
    ResponseEntity deleteGraph(@PathVariable long gid, @CurrentUser User currentUser) {
        graphService.deleteGraph(currentUser, gid);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

}
