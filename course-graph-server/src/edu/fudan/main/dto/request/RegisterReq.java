package edu.fudan.main.dto.request;

import edu.fudan.main.domain.UserType;

import javax.validation.constraints.NotNull;

public class RegisterReq {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private UserType type;

    public RegisterReq(@NotNull String email, @NotNull String name,
                       @NotNull String password, @NotNull UserType type) {
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
