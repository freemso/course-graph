package edu.fudan.main.dto.response;

import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;

public class UserPrivateResp extends UserPublicResp {

    /**
     * Email is not public data.
     * Thus only authorized request could get not-null email.
     */
    private String email;


    public UserPrivateResp() {
    }

    public UserPrivateResp(User user) {
        super(user);
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }
}
