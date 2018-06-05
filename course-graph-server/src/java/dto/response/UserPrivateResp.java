package java.dto.response;

import java.domain.User;
import java.domain.UserType;

public class UserPrivateResp extends UserPublicResp {

    /**
     * Email is not public data.
     * Thus only authorized request could get not-null email.
     */
    private String email;


    public UserPrivateResp(String name, long id, UserType type, String email) {
        super(name, id, type);
        this.email = email;
    }

    public UserPrivateResp(User user) {
        super(user);
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }
}
