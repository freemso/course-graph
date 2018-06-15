package edu.fudan.main.dto.request;

import edu.fudan.main.domain.Choice;
import edu.fudan.main.domain.QuestionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateQuestionReq {

    @NotBlank
    private String description;

    @NotNull
    private QuestionType type;

    private List<Choice> choices;

    private String answer;

    public CreateQuestionReq() {
    }

    public String getDescription() {
        return description;
    }

    public QuestionType getType() {
        return type;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }
}
