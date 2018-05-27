package main.java.dto;

public class RespLoginDTO {

    private String authentication;

    private long id;

    public RespLoginDTO(String authentication, long id) {
        this.authentication = authentication;
        this.id = id;
    }

    public String getAuthentication() {
        return authentication;
    }

    public long getId() {
        return id;
    }
}
