package edu.fudan.main.domain;


import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public abstract class AnswerEntry {

    @Id
    private Long answerEntryId;

    /**
     * The student who submitted this answer entry
     */
    @Relationship(type = "SUBMITTED_BY")
    private Student submitter;

    @Relationship(type = "ANSWER_TO")
    private Question question;

    @Property
    private String content;

    public AnswerEntry() {
    }

    public AnswerEntry(long id, Student submitter, Question question, String content) {
        this.answerEntryId = id;
        this.submitter = submitter;
        this.question = question;
        this.content = content;
    }

    public long getId() {
        return answerEntryId;
    }

    public void setId(long id) {
        this.answerEntryId = id;
    }

    public Student getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Student submitter) {
        this.submitter = submitter;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
