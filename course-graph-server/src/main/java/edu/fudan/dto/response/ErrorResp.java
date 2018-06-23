package edu.fudan.dto.response;

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

    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
