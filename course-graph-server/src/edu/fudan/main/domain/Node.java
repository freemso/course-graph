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
    private String nodeId;

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

    public Node(String id, String title) {
        this.nodeId = id;
        this.title = title;
        this.resourceList = new ArrayList<>();
        this.lectureList = new ArrayList<>();
        this.questionList = new ArrayList<>();
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getTitle() {
        return title;
    }

    public List<Resource> getResourceList() {
        List<Resource> resources = new ArrayList<>(resourceList);
        return resources;
    }

    public List<Lecture> getLectureList() {
        List<Lecture> lectures = new ArrayList<>(lectureList);
        return lectures;
    }

    public List<Question> getQuestionList() {
        List<Question> questions = new ArrayList<>(questionList);
        return questions;
    }

    public void addQuestion(Question question){
        this.questionList.add(question);
    }

    public void addResource(Resource resource){
        this.resourceList.add(resource);
    }

    public void addLecture(Lecture lecture){
        this.lectureList.add(lecture);
    }



}
