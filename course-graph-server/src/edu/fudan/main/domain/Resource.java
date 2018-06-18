package edu.fudan.main.domain;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Resource {

    @Id
    private Long resourceId;

    @Property
    private String title;

    @Property
    private String link;

    @Property
    private Long courseId;

    @Property
    private ResourceType type;

    public Resource() {
    }

    public Resource(Long resourceId, String title, String link, ResourceType type, long courseId) {
        this.resourceId = resourceId;
        this.title = title;
        this.link = link;
        this.type = type;
        this.courseId = courseId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public ResourceType getType(){
        return type;
    }

    public Long getCourseId() {
        return courseId;
    }
}
