package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Question {

    @Id
    private Long questionId;

    @Property
    private String description;

    @Property
    private QuestionType questionType;

    @Property
    private long courseId;

    @Relationship(type = "ANSWER_TO", direction = Relationship.INCOMING)
    private List<AnswerEntry> answerEntryList;

    public Question() {
    }

    public Question(long id, String description, QuestionType questionType, long courseId) {
        this.questionId = id;
        this.description = description;
        this.questionType = questionType;
        this.answerEntryList = new ArrayList<>();
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

    public List<AnswerEntry> getAnswerEntryList() {
        if(answerEntryList == null)
            return new ArrayList<>();
        return new ArrayList<>(answerEntryList);
    }

    public long getCourseId() {
        return courseId;
    }

    public void removeAnswers() {
        this.answerEntryList = null;
    }
}
