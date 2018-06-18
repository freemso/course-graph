package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Lecture {

    @Id
    private Long lectureId;

    @Property
    private String title;

    @Property
    private String link;

    @Property
    private Long courseId;

    public Lecture() {
    }

    public Lecture(Long lectureId, String title, String link, Long courseId) {
        this.lectureId = lectureId;
        this.title = title;
        this.link = link;
        this.courseId = courseId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Long getCourseId() {
        return courseId;
    }
}
