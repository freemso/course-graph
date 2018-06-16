package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalResourceTypeException extends RuntimeException{
    public IllegalResourceTypeException() {
        super("Illegal resource type.");
    }

    public IllegalResourceTypeException(String input) {
        super(String.format("%s is an illegal resource type", input));
    }
}

