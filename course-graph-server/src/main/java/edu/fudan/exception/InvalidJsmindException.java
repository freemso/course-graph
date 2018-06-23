package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidJsmindException extends RuntimeException {
    public InvalidJsmindException() {
        super("Invalid jsmind data.");
    }
}
