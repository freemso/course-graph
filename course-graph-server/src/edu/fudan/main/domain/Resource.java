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

    @Relationship(type = "HAS_RESOURCE", direction = Relationship.INCOMING)
    private Node node;

    @Property
    private ResourceType type;


    @Property
    private String originalFileName;

    public Resource() {
    }

    public Resource(Long resourceId, String title, String link, Node node, ResourceType type) {
        this.resourceId = resourceId;
        this.title = title;
        this.link = link;
        this.node = node;
        this.type = type;
        this.originalFileName = null;
    }

    public Resource(Long resourceId, String title, String link, Node node, ResourceType type, String originalFileName) {
        this.resourceId = resourceId;
        this.title = title;
        this.link = link;
        this.node = node;
        this.type = type;
        this.originalFileName = originalFileName;
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

    public Node getNode() {
        return node;
    }

    public void removeRelation() {
        this.node = null;
    }

    public ResourceType getType(){
        return type;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }
}
