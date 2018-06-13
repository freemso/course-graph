package edu.fudan.main.rest;

import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final UserService userService;

    @Autowired
    public CourseController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CourseMetaResp> createCourse(@CurrentUser User currentUser) {
        // TODO
        return null;
    }
}
