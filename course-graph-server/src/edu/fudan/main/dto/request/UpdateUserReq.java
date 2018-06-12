package edu.fudan.main.dto.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static edu.fudan.main.config.Constants.PASSWORD_REGEX;

/**
 * Data that needs update.
 * Must provide password to proceed this request.
 * Fields that are null will NOT be updated.
 */
public class UpdateUserReq {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String password;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid new password.")
    private String newPassword;

    public UpdateUserReq(String name, String email, String password, String newPassword) {
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
