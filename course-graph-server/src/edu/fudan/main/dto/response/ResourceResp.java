package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Resource;

public class ResourceResp {

    private long id;

    private String title;

    private String link;

    private String fileName;

    public ResourceResp() {
    }

    public ResourceResp(Resource resource) {
        this.id = resource.getResourceId();
        this.title = resource.getTitle();
        this.link = resource.getLink();
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
}
