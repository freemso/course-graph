package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateGraphReq {

    @NotBlank
    private String name;

    @NotNull
    private String description;

    private String jsmind;

    public CreateGraphReq() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getJsmind() {
        return jsmind;
    }
}
