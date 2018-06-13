package edu.fudan.main.dto.response;

import java.util.Date;

public class ErrorResp {
    private String error;
    private String details;
    private Date timestamp;

    public ErrorResp() {
    }

    public ErrorResp(String error, String details) {
        this.error = error;
        this.details = details;
        this.timestamp = new Date();
    }
}
