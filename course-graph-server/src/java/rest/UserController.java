package java.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.annotation.CurrentUser;
import java.domain.User;
import java.dto.request.RegisterReq;
import java.dto.response.UserPrivateResp;
import java.dto.response.UserPublicResp;

@Controller
@RequestMapping("/users")
public class UserController {

    /**
     * User registration request.
     * @param registerReq, required register form data
     * @return user private DTO with email field
     */
    @PostMapping
    ResponseEntity<UserPrivateResp> register(@Valid @RequestBody RegisterReq registerReq) {
        // TODO
        return null;
    }

    /**
     * Get meta data of the user with id equals to {uid}.
     * Both authorized and unauthorized requests are acceptable.
     * Only authorized currentUser with the same id as {uid} could get
     * user private data. Others get public data.
     * @param uid, id of the user to get
     * @param currentUser, nullable current authorized user
     * @return user public/private DTO
     */
    @GetMapping("/{uid}")
    ResponseEntity<UserPublicResp> getUser(@PathVariable long uid, @Nullable @CurrentUser User currentUser) {
        // TODO
        return null;
    }


}
