package edu.fudan.main.dto.request;

import edu.fudan.main.domain.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static edu.fudan.main.config.Constants.PASSWORD_REGEX;

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

    public RegisterReq() {
    }

    public RegisterReq(String email, String name, String password, UserType type) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.type = type;
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
}
