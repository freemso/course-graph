package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateCourseReq {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    public CreateCourseReq() {
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
