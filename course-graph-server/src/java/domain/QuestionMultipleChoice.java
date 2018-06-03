package main.java.domain;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

public class QuestionMultipleChoice extends Question {

    @Relationship(type = "HAS_CHOICE")
    private List<Choice> choices;

    /**
     * Correct answer should be equal to one of the choices' key
     */
    @Property
    private String correctAnswerKey;

    public QuestionMultipleChoice(long id, String description) {
        super(id, description, QuestionType.MULTIPLE_CHOICE);
        this.choices = new ArrayList<>();
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswerKey() {
        return correctAnswerKey;
    }

    public void setCorrectAnswerKey(String correctAnswerKey) {
        this.correctAnswerKey = correctAnswerKey;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }
}
