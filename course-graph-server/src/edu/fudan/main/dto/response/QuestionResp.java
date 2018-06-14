package edu.fudan.main.dto.response;

import edu.fudan.main.domain.*;

import java.util.List;

public class QuestionResp {
    //    question: # Response format
//    {
//        id : <integer>,
//            description : <string>,
//            type : <string>, # "MULTIPLE_CHOICE" or "SHORT_ANSWER"
//        choices : [ # presented only when type is "MULTIPLE_CHOICE"
//        {
//            key : <string>,
//                value : <string>
//        },
//        ...
//    ],
//        answer : <string>,
//            submission_num : <interger>, # OPTIONAL only available for teacher, otherwise it's null
//        correct_num : <interger> # OPTIONAL only available for teacher AND type is MULTIPLE_CHOICE, otherwise it's null
//    }
    private long id;

    private String description;

    private QuestionType questionType;

    private List<Choice> choices;

    private String answer;

    private int submissionNum;

    private int correctNum;

    public QuestionResp(long id, String description, QuestionType questionType,
                        List<Choice> choices, String answer, int submissionNum, int correctNum) {
        this.id = id;
        this.description = description;
        this.questionType = questionType;
        this.choices = choices;
        this.answer = answer;
        this.submissionNum = submissionNum;
        this.correctNum = correctNum;
    }

    public QuestionResp() {
    }

    public QuestionResp(Question question, UserType userType) {
        this.id = question.getQuestionId();
        this.description = question.getDescription();
        this.questionType = question.getQuestionType();
        this.submissionNum = -1;
        this.correctNum = -1;
        if (question.getQuestionType().equals(QuestionType.MULTIPLE_CHOICE)) {
            this.choices = ((QuestionMultipleChoice) question).getChoices();
            this.answer = ((QuestionMultipleChoice) question).getCorrectAnswerKey();
            if (userType.equals(UserType.TEACHER)) {
                this.answer = question.getAnswerEntryList().get(0).getContent();
                this.submissionNum = question.getAnswerEntryList().size();
                this.correctNum = 0;
                List<AnswerEntry> answerEntries = question.getAnswerEntryList();
                for (AnswerEntry answerEntry : answerEntries) {
                    if (((AnswerEntryMultipleChoice) answerEntry).isCorrect())
                        this.correctNum++;
                }
            }
        }
    }


    public void setSubmissionNum(int submissionNum) {
        this.submissionNum = submissionNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }
}
