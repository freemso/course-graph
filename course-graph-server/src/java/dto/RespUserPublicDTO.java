package main.java.dto;

import main.java.domain.UserType;

public class RespUserPublicDTO {
    private String name;

    private long id;

    private UserType type;

    public RespUserPublicDTO(String name, long id, UserType type) {
        this.name = name;
        this.id = id;
        this.type = type;
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
