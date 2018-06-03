package java.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class CourseGraph {

    @Relationship(type = "HAS_NODE")
    private Set<CourseNode> nodeSet;

    @Property
    private String name;

    @Property
    private String jsMindData;
}
