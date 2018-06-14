package edu.fudan.main.dto.request;

import javax.validation.constraints.NotBlank;

public class JsmindReq {

    @NotBlank
    private String jsmind;

    public JsmindReq() {
    }

    public String getJsmind() {
        return jsmind;
    }
}
