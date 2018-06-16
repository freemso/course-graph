package edu.fudan.main.exception;

import edu.fudan.main.domain.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundExeception extends RuntimeException{
    public ResourceNotFoundExeception(){
        super("Resource not found.");
    }

    public ResourceNotFoundExeception(long resourceId){
        super(String.format("Could not find resource '%d'.", resourceId));
    }
}

