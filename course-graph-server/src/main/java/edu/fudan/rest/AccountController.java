package edu.fudan.rest;

import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.UpdateUserReq;
import edu.fudan.dto.response.CourseMetaResp;
import edu.fudan.dto.response.UserPrivateResp;
import edu.fudan.model.CourseService;
import edu.fudan.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    private final CourseService courseService;

    @Autowired
    public AccountController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    /**
     * Get user private data.
     *
     * @param currentUser, current createToken user
     * @return user private data
     */
    @GetMapping
    @Authorization
    ResponseEntity<UserPrivateResp> getUserPrivate(@CurrentUser User currentUser) {
        UserPrivateResp userPrivateResp = new UserPrivateResp(currentUser);

        return new ResponseEntity<>(userPrivateResp, HttpStatus.OK);
    }

    /**
     * Update user profile.
     *
     * @param currentUser,   current createToken user
     * @param updateUserReq, data fields to update
     * @return updated user private data
     */
    @PutMapping
    @Authorization
    ResponseEntity updateUser(@CurrentUser User currentUser, @RequestBody UpdateUserReq updateUserReq) {
        userService.updateUser(currentUser, updateUserReq.getName(), updateUserReq.getEmail(),
                updateUserReq.getPassword(), updateUserReq.getNewPassword());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Delete the user with {uid}
     *
     * @param currentUser, current createToken user
     * @return empty response body
     */
    @DeleteMapping
    @Authorization
    ResponseEntity deleteUser(@CurrentUser User currentUser) {
        userService.deleteUser(currentUser);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/courses")
    @Authorization
    ResponseEntity<List<CourseMetaResp>> getCoursesOfUser(@CurrentUser User currentUser) {
        return new ResponseEntity<>(courseService.getAllCoursesOfUser(currentUser), HttpStatus.OK);
    }
}
