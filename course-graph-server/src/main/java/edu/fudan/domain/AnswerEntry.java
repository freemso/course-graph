package edu.fudan.domain;
import edu.fudan.domain.Question;
import org.neo4j.ogm.annotation.*;

@NodeEntity
public class AnswerEntry {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * The id of the student who submitted this answer entry
     */


    @Property
    private long submitterId;

    @Relationship(type = "ANSWER_TO")
    private Question question;

    @Property
    private String content;

    public AnswerEntry() {
    }

    public AnswerEntry(long submitterId, Question question, String content) {
        this.submitterId = submitterId;
        this.question = question;
        this.content = content;
    }

    public long getSubmitterId() {
        return submitterId;
    }

    public String getContent() {
        return content;
    }
}
