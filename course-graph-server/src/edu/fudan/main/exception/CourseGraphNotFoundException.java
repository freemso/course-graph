package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseGraphNotFoundException extends RuntimeException{
    public CourseGraphNotFoundException(){super("Course graph Not Found");}
    public CourseGraphNotFoundException(long courseGraphId) {
        super(String.format("Could not find course graph '%d'.", courseGraphId));
    }

}
