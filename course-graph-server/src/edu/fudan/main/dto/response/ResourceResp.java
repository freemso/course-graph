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
}
