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

    public void setNodeSet(Set<Node> nodeSet) {
        this.nodeSet = new HashSet<>(nodeSet);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph)) return false;
        return this.graphId.equals(((Graph) o).graphId);
    }

    @Override
    public int hashCode() {
        return graphId.hashCode();
    }
}
