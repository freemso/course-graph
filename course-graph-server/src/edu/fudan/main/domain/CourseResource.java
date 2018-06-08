package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "resource")
public class CourseResource {

    @Id
    private Long courseResourceId;

    @Property
    private String title;

    @Property
    private String link;

}
