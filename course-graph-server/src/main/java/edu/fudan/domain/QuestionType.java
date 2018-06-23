package edu.fudan.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.fudan.exception.IllegalQuestionTypeException;

public enum QuestionType {
    MULTIPLE_CHOICE("MULTIPLE_CHOICE"),
    SHORT_ANSWER("SHORT_ANSWER");

    private String text;

    QuestionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static QuestionType fromText(String text) {
        for (QuestionType t : QuestionType.values()) {
            if(t.toString().equals(text)) {
                return t;
            }
        }
        throw new IllegalQuestionTypeException();
    }
}
