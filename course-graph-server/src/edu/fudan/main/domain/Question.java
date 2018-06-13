package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public abstract class Question {

    @Id
    private Long questionId;

    @Property
    private String description;

    @Property
    private QuestionType questionType;

    @Relationship(type = "ANSWER_TO", direction = Relationship.INCOMING)
    private List<AnswerEntry> answerEntryList;

    public Question() {
    }

    public Question(long id, String description, QuestionType questionType) {
        this.questionId = id;
        this.description = description;
        this.questionType = questionType;
        this.answerEntryList = new ArrayList<>();
    }
}
