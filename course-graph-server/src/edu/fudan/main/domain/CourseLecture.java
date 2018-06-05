package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class CourseLecture {

    @Property
    private String title;

    @Property
    private String link;
}
