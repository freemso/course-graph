package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Graph {
    @Id
    private Long graphId;

    @Property
    private String name;

    @Property
    private String description;

    @Property
    private String jsMindData;

    @Property
    @CreatedDate
    private Date createdTime;

    @Property
    @LastModifiedDate
    private Date modifiedTime;

    @Relationship(type = "HAS_NODE")
    private Set<Node> nodeSet;

    @Relationship(type = "GRAPH_OF")
    private Course course;

    public Graph() {
    }

    public Graph(Long graphId, String name, String description, String jsMindData, Course course) {
        this.graphId = graphId;
        this.name = name;
        this.description = description;
        this.jsMindData = jsMindData;
        this.course = course;
    }

    public Long getGraphId() {
        return graphId;
    }

    public Set<Node> getNodeSet() {
        return nodeSet == null ? new HashSet<>() : nodeSet;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public String getJsMindData() {
        return jsMindData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setJsMindData(String jsMindData) {
        this.jsMindData = jsMindData;
    }
}
