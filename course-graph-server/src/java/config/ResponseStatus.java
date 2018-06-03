package java.config;

public enum ResponseStatus {
    SUCCESS(100, "Success"),
    EMAIL_OR_PASSWORD_ERROR(-1001, "Email or password error"),
    USER_NOT_FOUND(-1002, "User not found"),
    UNAUTHORIZED(-1003, "Unauthorized");

    /**
     * Response code
     */
    private int code;

    /**
     * Description of the response
     */
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
