package edu.fudan.main.exception;

public class IllegalResourceTypeException extends RuntimeException{
    public IllegalResourceTypeException() {
        super("Illegal resource type.");
    }

    public IllegalResourceTypeException(String input) {
        super(String.format("%s is an illegal resource type", input));
    }
}

