package edu.fudan.dto.response;

import edu.fudan.domain.User;
import edu.fudan.domain.UserType;

public class UserPublicResp {
    private String name;

    private long id;

    private UserType type;

    public UserPublicResp() {
    }

    public UserPublicResp(User user) {
        this.name = user.getName();
        this.id = user.getUserId();
        this.type = user.getType();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }
}
