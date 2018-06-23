package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalQuestionTypeException extends RuntimeException {

    public IllegalQuestionTypeException() {
        super("Illegal question type exception");
    }
}
