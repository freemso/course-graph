package edu.fudan.dto.request;

import edu.fudan.domain.ResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddUrlResourceReq {

    @NotBlank
    private String title;

    @NotBlank
    private String link;

    @NotNull
    private ResourceType type;

    public AddUrlResourceReq(){

    }

    public AddUrlResourceReq(@NotBlank String title, @NotBlank String link, @NotNull ResourceType type) {
        this.title = title;
        this.link = link;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }


}
