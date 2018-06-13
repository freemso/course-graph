package edu.fudan.main.domain;

public class AnswerEntryShortAnswer extends AnswerEntry {

    public AnswerEntryShortAnswer() {
    }

    public AnswerEntryShortAnswer(long id, Student creator, String content,
                                  QuestionShortAnswer questionShortAnswer) {
        super(id, creator, questionShortAnswer, content);
    }
}
