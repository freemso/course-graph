package edu.fudan.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResp {

    private UserPublicResp user;

    @JsonProperty("token")
    private String authentication;

    public AuthenticationResp() {
    }

    public AuthenticationResp(String authentication, UserPublicResp user) {
        this.authentication = authentication;
        this.user = user;
    }

    public String getAuthentication(){
        return authentication;
    }


    public UserPublicResp getUser() {
        return user;
    }
}
