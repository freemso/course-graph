package edu.fudan.main.dto.request;

import javax.validation.constraints.NotBlank;

public class UpdateJsmindReq {

    @NotBlank
    private String jsmind;

    public UpdateJsmindReq() {
    }

    public String getJsmind() {
        return jsmind;
    }
}
