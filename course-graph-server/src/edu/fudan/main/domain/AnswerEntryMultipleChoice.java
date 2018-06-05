package edu.fudan.main.domain;


import org.neo4j.ogm.annotation.Property;

public class AnswerEntryMultipleChoice extends AnswerEntry {

    @Property
    private boolean correct;

    public AnswerEntryMultipleChoice(long id, Student creator, String content,
                                     QuestionMultipleChoice questionMultipleChoice,
                                     boolean correct) {
        super(id, creator, questionMultipleChoice, content);
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
