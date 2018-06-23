package edu.fudan.dto.request;

import javax.validation.constraints.NotBlank;

public class JoinCourseReq {

    @NotBlank
    private String code;

    public JoinCourseReq() {
    }

    public String getCode() {
        return code;
    }
}
