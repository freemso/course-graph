package java.rest;


import java.annotation.Authorization;
import java.annotation.CurrentUser;
import java.domain.User;
import java.dto.request.LoginReq;
import java.dto.response.AuthenticationResp;
import java.model.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final UserService userService;

    @Autowired
    public TokenController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User login request.
     * @param loginReq, required login form data
     * @return login response DTO
     */
    @PostMapping
    ResponseEntity<AuthenticationResp> login(@Valid @RequestBody LoginReq loginReq) {
        AuthenticationResp authenticationResp = userService.login(
                loginReq.getEmail(), loginReq.getPassword());

        return new ResponseEntity<>(authenticationResp, HttpStatus.CREATED);
    }

    /**
     * User logout request.
     * @param currentUser, authorized current user
     * @return response with empty body
     */
    @Authorization
    @DeleteMapping
    ResponseEntity logout(@CurrentUser User currentUser) {
        userService.logout(currentUser.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
