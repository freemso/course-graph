package edu.fudan.main.rest;

import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.model.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/graphs")
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @GetMapping("/{gid}")
    ResponseEntity<GraphMetaResp> getGraph(@PathVariable long gid) {
//        return new ResponseEntity<>(graphService.)
        // TODO
        return null;
    }

}
