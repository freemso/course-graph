package edu.fudan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IO Exception")
public class LectureIOException extends RuntimeException{
    public LectureIOException(){
        super("Lecture IO Exception");
    }

    public LectureIOException(String message){
        super(message);
    }
}
