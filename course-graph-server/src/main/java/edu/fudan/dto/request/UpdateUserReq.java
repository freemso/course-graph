package edu.fudan.dto.request;



import edu.fudan.Application;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Data that needs update.
 * Must provide password to proceed this request.
 * Fields that are null will NOT be updated.
 */
public class UpdateUserReq {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = Application.PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String password;

    @Pattern(regexp = Application.PASSWORD_REGEX, message = "invalid new password.")
    private String newPassword;

    public UpdateUserReq() {
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
