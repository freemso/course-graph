package main.java.dto;

import main.java.domain.UserType;

public class RespUserPrivateDTO extends RespUserPublicDTO {

    /**
     * Email is not public data.
     * Thus only authorized request could get not-null email.
     */
    private String email;


    public RespUserPrivateDTO(String name, long id, UserType type, String email) {
        super(name, id, type);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
