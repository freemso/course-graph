package edu.fudan.main.dto.response;

import java.util.Date;

public class ErrorResp {
    private String error;
    private String details;
    private Date timestamp;

    public ErrorResp(String error, String details, Date timestamp) {
        this.error = error;
        this.details = details;
        this.timestamp = timestamp;
    }
}
