package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(){super("Course Not Found");}
    public CourseNotFoundException(long courseId) {
        super(String.format("Could not find course '%d'.", courseId));
    }

}
