package edu.fudan.main.exception;

public class ResourceNotFoundExeception extends RuntimeException{
    public ResourceNotFoundExeception(){
        super("Resource not found.");
    }

    public ResourceNotFoundExeception(long resourceId){
        super(String.format("Could not find resource '%d'.", resourceId));
    }
}

