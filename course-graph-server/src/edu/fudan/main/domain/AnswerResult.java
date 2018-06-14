package edu.fudan.main.domain;

public class AnswerResult {
    long id;
    boolean correct;

    public AnswerResult(long id, boolean correct) {
        this.id = id;
        this.correct = correct;
    }
}
