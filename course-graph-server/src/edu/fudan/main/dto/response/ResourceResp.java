package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Resource;
import edu.fudan.main.domain.ResourceType;

public class ResourceResp {

    private long id;

    private String title;

    private String link;

    private String fileName;

    private ResourceType type;

    public ResourceResp() {
    }

    public ResourceResp(Resource resource) {
        this.id = resource.getResourceId();
        this.title = resource.getTitle();
        this.link = resource.getLink();
        this.type = resource.getType();
        this.fileName = resource.getOriginalFileName();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLink() {
        return link;
    }

    public ResourceType getType() {
        return type;
    }
}
