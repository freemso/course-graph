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

    @Relationship(type = "HAS_NODE", direction = Relationship.INCOMING)
    private Graph graph;

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
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getTitle() {
        return title;
    }

    public List<Resource> getResourceList() {
        return resourceList == null ? new ArrayList<>() : resourceList;
    }

    public List<Lecture> getLectureList() {
        return lectureList == null ? new ArrayList<>() : lectureList;
    }

    public List<Question> getQuestionList() {
        return questionList == null ? new ArrayList<>() : questionList;
    }

    public Graph getGraph() {
        return graph;
    }

    public void addLecture(Lecture lecture) {
        if (lectureList == null) {
            lectureList = new ArrayList<>();
        }
        this.lectureList.add(lecture);
    }

    public void addQuestion(Question question) {
        if (questionList == null) {
            questionList = new ArrayList<>();
        }
        this.questionList.add(question);
    }

    public void addResource(Resource resource) {
        if (resourceList == null) {
            resourceList = new ArrayList<>();
        }
        this.resourceList.add(resource);
    }

    public void removeRelations() {
        this.graph = null;
        this.resourceList = null;
        this.questionList = null;
        this.lectureList = null;
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
