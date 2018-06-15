package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Lecture;

public class LectureResp {

    private long id;

    private String title;

    private String link;

    public LectureResp() {
    }

    public LectureResp(Lecture lecture) {
        this.id = lecture.getLectureId();
        this.title = lecture.getTitle();
        this.link = lecture.getTitle();
    }
}
