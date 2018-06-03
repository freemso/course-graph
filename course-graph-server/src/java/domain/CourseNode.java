package java.domain;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class CourseNode {

    @Id
    private long id;

    @Property
    private String title;

    @Relationship(type = "HAS_RESOURCE")
    private List<CourseResource> resourceList;

    @Relationship(type = "HAS_LECTURE")
    private List<CourseLecture> lectureList;

    @Relationship(type = "HAS_QUESTION")
    private List<Question> questionList;

    public CourseNode(long id, String title) {
        this.id = id;
        this.title = title;
        this.resourceList = new ArrayList<>();
        this.lectureList = new ArrayList<>();
        this.questionList = new ArrayList<>();
    }
}
