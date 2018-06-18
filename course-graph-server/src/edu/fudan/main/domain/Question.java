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

    @Property
    private Long courseId;

    @Relationship(type = "ANSWER_TO", direction = Relationship.INCOMING)
    private List<AnswerEntry> answerEntryList;

    public Question() {
    }

    public Question(long id, String description, QuestionType questionType, long courseId) {
        this.questionId = id;
        this.description = description;
        this.questionType = questionType;
        this.courseId = courseId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getDescription() {
        return description;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public long getCourseId() {
        return courseId;
    }

    public List<AnswerEntry> getAnswerEntryList() {
        return answerEntryList == null ? new ArrayList<>() : answerEntryList;
    }
}
