package java.dto.request;


import javax.validation.constraints.NotNull;

/**
 * Data that needs update.
 * Must provide password to proceed this request.
 * Fields that are null will NOT be updated.
 */
public class UpdateUserReq {

    private String name;

    private String email;

    @NotNull
    private String password;

    private String newPassword;

    public UpdateUserReq(String name, String email,
                         @NotNull String password, String newPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
