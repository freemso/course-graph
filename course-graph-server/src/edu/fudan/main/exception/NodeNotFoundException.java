package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NodeNotFoundException extends RuntimeException{
    public NodeNotFoundException() {
        super("Node Not Found");
    }

    public NodeNotFoundException(String nodeId) {
        super(String.format("Could not find course '%s'.", nodeId));
    }
}
