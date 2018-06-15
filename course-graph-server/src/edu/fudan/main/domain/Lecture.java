package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Lecture {

    @Id
    private Long lectureId;

    @Property
    private String title;

    @Property
    private String link;

    @Relationship(type = "HAS_LECTURE", direction = Relationship.INCOMING)
    private Node node;

    public Lecture() {
    }

    public Long getLectureId() {
        return lectureId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Node getNode() {
        return node;
    }

    public void removeRelation() {
        this.node = null;
    }
}
