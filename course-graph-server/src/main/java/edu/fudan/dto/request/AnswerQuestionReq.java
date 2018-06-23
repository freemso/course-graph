package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;

public class AnswerQuestionReq {

    @NotBlank
    private String answer;

    public AnswerQuestionReq() {
    }

    public String getAnswer() {
        return answer;
    }
}
