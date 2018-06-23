package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid verification code")
public class InvalidVerificationCodeException extends  RuntimeException{
    public InvalidVerificationCodeException() {
        super("Invalid verification code.");
    }
}
