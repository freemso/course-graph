package edu.fudan.main.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static edu.fudan.main.config.Constants.PASSWORD_REGEX;

public class LoginReq {

    @Email(message = "invalid email.")
    private String email;

    @Pattern(regexp = PASSWORD_REGEX, message = "invalid password.")
    private String password;

    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
