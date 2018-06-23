package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException() {
        super("Student already in this course.");
    }
}
