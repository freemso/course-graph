package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException() {
        super("Question Not Found");
    }

    public QuestionNotFoundException(long questionId) {
        super(String.format("Could not find question '%d'.", questionId));
    }

}