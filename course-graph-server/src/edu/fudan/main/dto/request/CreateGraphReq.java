package edu.fudan.main.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateGraphReq {

    @NotBlank
    private String name;

    private String description;

    public CreateGraphReq() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
