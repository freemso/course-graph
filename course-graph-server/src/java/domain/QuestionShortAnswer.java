package java.domain;

public class QuestionShortAnswer extends Question {

    public QuestionShortAnswer(long id, String description) {
        super(id, description, QuestionType.SHORT_ANSWER);
    }
}
