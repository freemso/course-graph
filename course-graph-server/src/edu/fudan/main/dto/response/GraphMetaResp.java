package edu.fudan.main.dto.response;


import edu.fudan.main.domain.Graph;

import java.util.Date;

public class GraphMetaResp {

    private String name;

    private long id;

    private String description;

    private Date createdTime;

    private Date modifiedTime;

    public GraphMetaResp() {
    }

    public GraphMetaResp(Graph graph) {
        this.name = graph.getName();
        this.id = graph.getGraphId();
        this.description = graph.getDescription();
        this.createdTime = graph.getCreatedTime();
        this.modifiedTime = graph.getModifiedTime();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
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
}
