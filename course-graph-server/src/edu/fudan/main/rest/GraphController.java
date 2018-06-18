package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.UpdateGraphMetaReq;
import edu.fudan.main.dto.request.UpdateJsmindReq;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.dto.response.JsmindResp;
import edu.fudan.main.model.GraphService;
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
    ResponseEntity<GraphMetaResp> getGraphMeta(@PathVariable long gid) {
        return new ResponseEntity<>(graphService.getGraphMeta(gid), HttpStatus.OK);
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
    ResponseEntity<JsmindResp> getGraphJsmind(@PathVariable long gid) {
        return new ResponseEntity<>(graphService.getJsmind(gid), HttpStatus.OK);
    }

    @PutMapping("/{gid}/jsmind")
    @Authorization
    ResponseEntity<JsmindResp> updateGraphJsmind(@PathVariable long gid,
                                                 @CurrentUser User currentUser,
                                                 @Valid @RequestBody UpdateJsmindReq updateJsmindReq) {
        return new ResponseEntity<>(graphService.updateJsmind(currentUser, gid, updateJsmindReq.getJsmind()), HttpStatus.OK);
    }

    @DeleteMapping("/{gid}")
    @Authorization
    ResponseEntity deleteGraph(@PathVariable long gid, @CurrentUser User currentUser) {
        graphService.deleteGraph(currentUser, gid);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

}
