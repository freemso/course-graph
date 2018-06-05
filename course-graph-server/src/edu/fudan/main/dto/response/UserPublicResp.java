package edu.fudan.main.dto.response;

import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;

public class UserPublicResp {
    private String name;

    private long id;

    private UserType type;

    public UserPublicResp(String name, long id, UserType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public UserPublicResp(User user) {
        this.name = user.getName();
        this.id = user.getId();
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
