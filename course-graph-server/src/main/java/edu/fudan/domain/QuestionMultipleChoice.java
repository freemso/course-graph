package edu.fudan.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class QuestionMultipleChoice extends Question {

    @Relationship(type = "HAS_CHOICE")
    private List<Choice> choices;

    /**
     * Correct answer should be equal to one of the choices' key
     */
    @Property
    private String correctAnswerKey;

    public QuestionMultipleChoice() {
    }

    public QuestionMultipleChoice(long id, String description, List<Choice> choices, String correctAnswerKey, long courseId) {
        super(id, description, QuestionType.MULTIPLE_CHOICE, courseId);
        this.choices = choices;
        this.correctAnswerKey = correctAnswerKey;
    }

    public List<Choice> getChoices() {
        List<Choice> choices = new ArrayList<>(this.choices);
        return choices;
    }

    public String getCorrectAnswerKey() {
        return correctAnswerKey;
    }

    public void removeChoices() {
        this.choices = null;
    }
}
