package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException() {
        super("Course code invalid");
    }
}
