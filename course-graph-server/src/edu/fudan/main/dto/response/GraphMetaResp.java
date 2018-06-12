package edu.fudan.main.dto.response;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class GraphMetaResp {
    String name;
    Long id;
    String description;
    String createdTime;
    String modifiedTime;

    public GraphMetaResp(String name, Long id, String description, String createdTime, String modifiedTime) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
