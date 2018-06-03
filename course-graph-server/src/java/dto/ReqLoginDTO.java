package java.dto;

import javax.validation.constraints.NotNull;

public class ReqLoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public ReqLoginDTO(@NotNull String email, @NotNull String password) {
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
