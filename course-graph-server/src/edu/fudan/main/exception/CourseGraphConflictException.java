package edu.fudan.main.exception;

public class CourseGraphConflictException extends RuntimeException{
    public CourseGraphConflictException(){
        super("This course graph has already existed.");
    }

    public CourseGraphConflictException(long courseGraphId){
        super(String.format("Course graph id '%l' has already existed.", courseGraphId));
    }

    public CourseGraphConflictException(String courseGraphName){
        super(String.format("Course graph id '%s' has already existed.", courseGraphName));
    }
}
