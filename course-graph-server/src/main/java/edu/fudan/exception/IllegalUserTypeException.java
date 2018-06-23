package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUserTypeException extends RuntimeException {
    public IllegalUserTypeException() {
        super("Illegal user type.");
    }

    public IllegalUserTypeException(String input) {
        super(String.format("%s is an illegal user type", input));
    }
}
