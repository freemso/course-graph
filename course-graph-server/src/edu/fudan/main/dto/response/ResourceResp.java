package edu.fudan.main.dto.response;

import edu.fudan.main.domain.Resource;

public class ResourceResp {

    private long id;

    private String title;

    private String link;

    public ResourceResp() {
    }

    public ResourceResp(Resource resource) {
        this.id = resource.getResourceId();
        this.title = resource.getTitle();
        this.link = resource.getLink();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
