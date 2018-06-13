package edu.fudan.main.exception;

public class GraphConflictException extends RuntimeException{
    public GraphConflictException(){
        super("This course graph has already existed.");
    }

    public GraphConflictException(long courseGraphId){
        super(String.format("Course graph id '%l' has already existed.", courseGraphId));
    }

    public GraphConflictException(String courseGraphName){
        super(String.format("Course graph id '%s' has already existed.", courseGraphName));
    }
}
