package edu.fudan.main.domain;


import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Node {

    @Id
    private Long nodeId;

    @Property
    private String title;

    @Relationship(type = "HAS_RESOURCE")
    private List<Resource> resourceList;

    @Relationship(type = "HAS_LECTURE")
    private List<Lecture> lectureList;

    @Relationship(type = "HAS_QUESTION")
    private List<Question> questionList;

    public Node() {
    }

    public Node(long id, String title) {
        this.nodeId = id;
        this.title = title;
        this.resourceList = new ArrayList<>();
        this.lectureList = new ArrayList<>();
        this.questionList = new ArrayList<>();
    }
}
