package edu.fudan.main.dto.request;

import javax.validation.constraints.NotBlank;

public class AddToCourseReq {

    @NotBlank
    private String code;

    public AddToCourseReq() {
    }

    public String getCode() {
        return code;
    }
}
