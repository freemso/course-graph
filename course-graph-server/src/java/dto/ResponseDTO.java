package main.java.dto;

import main.java.config.ResponseStatus;

/**
 * Wrapper for REST response object, including status code, message, and content
 * @author freemso
 */
public class ResponseDTO {

    /**
     * Status code, mostly used to identify the type of the error.
     */
    private int code;

    /**
     * A description of the response, mostly used to pass the error message.
     */
    private String message;

    /**
     * The actual content of response.
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }


    /**
     * Response with default message and empty content.
     * @param status, ResponseStatus
     */
    public ResponseDTO(ResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = null;
    }

    /**
     * Response with message and empty content.
     * @param status, ResponseStatus
     * @param message, response message
     */
    public ResponseDTO(ResponseStatus status, String message) {
        this.code = status.getCode();
        this.message = message;
        this.content = null;
    }

    /**
     * Response with default message and content.
     * @param status, ResponseStatus
     * @param content, actual content
     */
    public ResponseDTO(ResponseStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    /**
     * Response with message and content.
     * @param status, ResponseStatus
     * @param message, response message
     * @param content, actual content
     */
    public ResponseDTO(ResponseStatus status, String message, Object content) {
        this.code = status.getCode();
        this.message = message;
        this.content = content;
    }


    public static ResponseDTO successResponse(Object content) {
        return new ResponseDTO(ResponseStatus.SUCCESS, content);
    }

    public static ResponseDTO successResponse() {
        return new ResponseDTO(ResponseStatus.SUCCESS);
    }


}
