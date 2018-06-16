package edu.fudan.main.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.fudan.main.exception.IllegalUserTypeException;

public enum ResourceType {
    FILE("FILE"),
    URL("URL");

    private String text;

    ResourceType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static ResourceType fromText(String text) {
        for (ResourceType t : ResourceType.values()) {
            if(t.toString().equals(text)) {
                return t;
            }
        }
        throw new IllegalUserTypeException();
    }

}
