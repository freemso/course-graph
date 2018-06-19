package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IO Exception")
public class ResourceIOException extends RuntimeException {
    public ResourceIOException(){
        super("Resource IO Exception");
    }

    public ResourceIOException(String message){
        super(message);
    }
}