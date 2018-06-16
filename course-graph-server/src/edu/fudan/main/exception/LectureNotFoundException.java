package edu.fudan.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Lecture not found")
public class LectureNotFoundException extends RuntimeException{
    public LectureNotFoundException() {
        super("Lecture Not Found");
    }

    public LectureNotFoundException(String lectureId) {
        super(String.format("Could not find lecture '%s'.", lectureId));
    }
}