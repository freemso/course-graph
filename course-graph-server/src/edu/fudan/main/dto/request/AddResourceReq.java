package edu.fudan.main.dto.request;

import edu.fudan.main.domain.ResourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddResourceReq {

    @NotBlank
    String title;

    @NotBlank
    String link;


    byte[] file;

    @NotNull
    ResourceType type;

    public AddResourceReq(@NotBlank String title, @NotBlank String link, byte[] file, @NotNull ResourceType type) {
        this.title = title;
        this.link = link;
        this.file = file;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public byte[] getFile() {
        return file;
    }

    public ResourceType getType() {
        return type;
    }
}
