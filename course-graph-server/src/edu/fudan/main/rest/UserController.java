package edu.fudan.main.rest;

import edu.fudan.main.dto.request.RegisterReq;
import edu.fudan.main.dto.response.UserPrivateResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User registration request.
     * @param registerReq, required createUser form data
     * @return user private DTO with email field
     */
    @PostMapping
    ResponseEntity<UserPrivateResp> register(@Valid @RequestBody RegisterReq registerReq) {
        UserPrivateResp userPrivateResp = this.userService.createUser(registerReq.getEmail(),
                registerReq.getName(), registerReq.getPassword(), registerReq.getType());

        return new ResponseEntity<>(userPrivateResp, HttpStatus.CREATED);
    }

    /**
     * Get meta data of the user with id equals to {uid}.
     * Both authorized and unauthorized requests are acceptable.
     * Only authorized currentUser with the same id as {uid} could get
     * user private data. Others get public data.
     * @param uid, id of the user to get
     * @return user public data
     */
    @GetMapping("/{uid}")
    ResponseEntity<UserPublicResp> getUser(@PathVariable long uid) {
        UserPublicResp userPublicResp = this.userService.getUserPublic(uid);

        return new ResponseEntity<>(userPublicResp, HttpStatus.OK);
    }


}
