package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class Lecture {

    @Id
    private Long lectureId;

    @Property
    private String title;

    @Property
    private String link;

    public Lecture() {
    }
}
