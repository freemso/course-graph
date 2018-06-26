package edu.fudan.dto.request;

import edu.fudan.domain.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static edu.fudan.Application.PASSWORD_REGEX;

public class RegisterReq {

    @Email(message = "invalid email.")
    @NotBlank
    private String email;

    @NotBlank(message = "name can't be empty")
    private String name;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.")
    @NotBlank
    private String password;

    @NotNull
    private UserType type;

    @NotNull
    private String verificationCode;

    public RegisterReq() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public String getVerificationCode() { return verificationCode; }
}
