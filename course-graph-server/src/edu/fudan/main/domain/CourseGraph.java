package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.*;

import java.util.Set;

@NodeEntity
public class CourseGraph {
    @Id
    private Long courseGraphId;


    @Relationship(type = "HAS_NODE")
    private Set<CourseNode> nodeSet;

    @Property@Index(unique = true)
    private String name;

    @Property
    private String jsMindData;

    public CourseGraph(Long courseGraphId, String name) {
        this.courseGraphId = courseGraphId;
        this.name = name;
    }

    public Long getCourseGraphId() {
        return courseGraphId;
    }

    public Set<CourseNode> getNodeSet() {
        return nodeSet;
    }

    public String getName() {
        return name;
    }

    public String getJsMindData() {
        return jsMindData;
    }

    public void addCourseNode(CourseNode courseNode){
        this.nodeSet.add(courseNode);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJsMindData(String jsMindData) {
        this.jsMindData = jsMindData;
    }
}
