package edu.fudan.main.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailConflictException extends RuntimeException {

    public EmailConflictException() {
        super("This email has already existed.");
    }

    public EmailConflictException(String email) {
        super(String.format("Email '%s' has already existed.", email));
    }
}
