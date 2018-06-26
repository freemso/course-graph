package edu.fudan.dto.response;

import edu.fudan.domain.*;

import java.util.Collections;
import java.util.List;

public class QuestionResp {

    private long id;

    private String description;

    private QuestionType questionType;

    private List<Choice> choices;

    private String answer;

    private int submissionNum;

    private int correctNum;

    public QuestionResp() {
    }

    public QuestionResp(Question question, User currentUser) {
        // Fill the basic info of the question
        this.id = question.getQuestionId();
        this.description = question.getDescription();
        this.questionType = question.getQuestionType();

        if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
            List<Choice> choices = ((QuestionMultipleChoice) question).getChoices();
            // Collections.sort(this.choices);
            Collections.sort(choices, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
            this.choices = choices;
        }

        switch (currentUser.getType()) {
            case TEACHER: {
                // Teacher can get statistical data
                this.submissionNum = question.getAnswerEntryList().size();
                this.correctNum = 0;
                if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
                    String correctKey = ((QuestionMultipleChoice) question).getCorrectAnswerKey();
                    for (AnswerEntry answerEntry : question.getAnswerEntryList()) {
                        // Check if the answer is correct
                        if (answerEntry.getContent().equals(correctKey)) {
                            this.correctNum++;
                        }
                    }
                }
                break;
            }
            case STUDENT: {
                // Student can get his own answer
                for (AnswerEntry answerEntry : question.getAnswerEntryList()) {
                    // Check if there is answer to the question
                    if (answerEntry.getSubmitterId() == currentUser.getUserId()) {
                        answer = answerEntry.getContent();
                    }
                }
                break;
            }
        }
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSubmissionNum() {
        return submissionNum;
    }

    public int getCorrectNum() {
        return correctNum;
    }
}
