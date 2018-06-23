package edu.fudan.rest;

import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.config.Constants;
import edu.fudan.domain.User;
import edu.fudan.dto.request.MailRelatedReq;
import edu.fudan.dto.request.RegisterReq;
import edu.fudan.dto.response.AuthenticationResp;
import edu.fudan.dto.response.UserPrivateResp;
import edu.fudan.dto.response.UserPublicResp;
import edu.fudan.model.MailService;
import edu.fudan.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Controller
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * User registration request.
     *
     * @param registerReq, required createUser form data
     * @return user private DTO with email field
     */
    @PostMapping
    ResponseEntity<UserPrivateResp> register(@Valid @RequestBody RegisterReq registerReq) {
        UserPrivateResp userPrivateResp = this.userService.createUser(registerReq.getEmail(),
                registerReq.getName(), registerReq.getPassword(), registerReq.getType(), registerReq.getVerificationCode());
        return new ResponseEntity<>(userPrivateResp, HttpStatus.CREATED);
    }


    @PostMapping("/verification_code")
    ResponseEntity<String> sendCode(@RequestBody MailRelatedReq email) {
        String code = userService.registerVerification(email.getEmail());
        mailService.sendMessage(email.getEmail(), "Your verification code is " + code + ", it will expire after 72h :)");
        return new ResponseEntity<String>("Please check your mail", HttpStatus.OK);
    }

    /**
     * Get meta data of the user with id equals to {uid}.
     * Both authorized and unauthorized requests are acceptable.
     * Only authorized currentUser with the same id as {uid} could get
     * user private data. Others get public data.
     *
     * @param uid, id of the user to get
     * @return user public data
     */
    @GetMapping("/{uid}")
    ResponseEntity<UserPublicResp> getUser(@PathVariable long uid) {
        UserPublicResp userPublicResp = this.userService.getUserPublic(uid);

        return new ResponseEntity<>(userPublicResp, HttpStatus.OK);
    }


    @PostMapping("/forget_password")
    ResponseEntity sendPasswordLink(@RequestBody MailRelatedReq email) {
        AuthenticationResp authenticationResp = userService.createToken(email.getEmail());
        String message = "Click following link to reset your password: \n" +
                "www.coursegraph.top/reset_password?token=" +
                authenticationResp.getAuthentication();
        mailService.sendMessage(email.getEmail(), message);
        return new ResponseEntity(null, HttpStatus.OK);
    }


    @PostMapping("/reset_password")
    @Authorization
    ResponseEntity resetPassword(@CurrentUser User user, @RequestParam("password") @Pattern(regexp = Constants.PASSWORD_REGEX) String password) {
        userService.resetPassword(user, password);
        return new ResponseEntity(null, HttpStatus.OK);
    }


}
