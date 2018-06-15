package edu.fudan.main.domain;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Node {

    @Id@Index(unique = true)
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
        if(this.resourceList == null)
            return new ArrayList<>();
        List<Resource> resources = new ArrayList<>(resourceList);
        return resources;
    }

    public List<Lecture> getLectureList() {
        if(this.lectureList == null)
            return new ArrayList<>();
        List<Lecture> lectures = new ArrayList<>(lectureList);
        return lectures;
    }

    public List<Question> getQuestionList() {
        if(this.questionList == null)
            return new ArrayList<>();
        List<Question> questions = new ArrayList<>(questionList);
        return questions;
    }

    public void addQuestion(Question question) {
        this.questionList.add(question);
    }

    public void addResource(Resource resource) {
        this.resourceList.add(resource);
    }

    public void addLecture(Lecture lecture) {
        this.lectureList.add(lecture);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return nodeId.equals(node.nodeId);
    }

    @Override
    public int hashCode() {
        return nodeId.hashCode();
    }
//
//    @Override
//    public int hashCode() {
//        int result = nodeId.hashCode();
//        result = 31 * result + title.hashCode();
//        return result;
//    }
}
