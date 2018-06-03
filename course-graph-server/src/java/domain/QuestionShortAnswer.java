package main.java.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class QuestionShortAnswer extends Question {

    public QuestionShortAnswer(long id, String description) {
        super(id, description, QuestionType.SHORT_ANSWER);
    }
}
