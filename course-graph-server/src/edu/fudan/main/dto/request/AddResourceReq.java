package edu.fudan.main.dto.request;

import edu.fudan.main.domain.ResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddResourceReq {

    @NotBlank
    String title;

    @NotBlank
    String link;


    @NotNull
    ResourceType type;

    public AddResourceReq(){

    }

    public AddResourceReq(@NotBlank String title, @NotBlank String link, @NotNull ResourceType type) {
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
