package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GraphNotFoundException extends RuntimeException{
    public GraphNotFoundException(){super("Course graph Not Found");}
    public GraphNotFoundException(long courseGraphId) {
        super(String.format("Could not find course graph '%d'.", courseGraphId));
    }

}
