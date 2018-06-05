package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.UpdateUserReq;
import edu.fudan.main.dto.response.UserPrivateResp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * Get user private data.
     * @param currentUser, current login user
     * @return user private data
     */
    @GetMapping
    @Authorization
    ResponseEntity<UserPrivateResp> getUserPrivate(@CurrentUser User currentUser) {
        // TODO
        return null;
    }

    /**
     * Update user profile.
     * @param currentUser, current login user
     * @param updateUserReq, data fields to update
     * @return updated user private data
     */
    @PutMapping
    @Authorization
    ResponseEntity<UserPrivateResp> updateUser(@CurrentUser User currentUser,
                                               @RequestBody UpdateUserReq updateUserReq) {
        // TODO
        return null;
    }

    /**
     * Delete the user with {uid}
     * @param currentUser, current login user
     * @return empty response body
     */
    @DeleteMapping
    @Authorization
    ResponseEntity deleteUser(@CurrentUser User currentUser) {
        // TODO
        return null;
    }
}
