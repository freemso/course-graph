package edu.fudan.main.dto.response;

public class AuthenticationResp {

    private UserPublicResp user;

    private String token;

    public AuthenticationResp(String token, UserPublicResp user) {
        this.token = token;
        this.user = user;
    }
}
