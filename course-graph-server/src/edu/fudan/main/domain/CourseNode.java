package edu.fudan.main.domain;


import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class CourseNode {

    @Id
    private Long courseNodeId;

    @Property
    private String title;

    @Relationship(type = "HAS_RESOURCE")
    private List<CourseResource> resourceList;

    @Relationship(type = "HAS_LECTURE")
    private List<CourseLecture> lectureList;

    @Relationship(type = "HAS_QUESTION")
    private List<Question> questionList;

    public CourseNode(long id, String title) {
        this.courseNodeId = id;
        this.title = title;
        this.resourceList = new ArrayList<>();
        this.lectureList = new ArrayList<>();
        this.questionList = new ArrayList<>();
    }
}