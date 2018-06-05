package java.dto.request;

import javax.validation.constraints.NotNull;

public class LoginReq {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginReq(@NotNull String email, @NotNull String password) {
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
