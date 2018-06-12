package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)

public class CourseConflictException extends EmailConflictException{
    public CourseConflictException(){
        super("This course has already existed.");
    }

    public CourseConflictException(String courseCode){
        super(String.format("Course Code '%s' has already existed.", courseCode));
    }
}