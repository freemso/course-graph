package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.AddToCourseReq;
import edu.fudan.main.dto.request.CreateCourseReq;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.model.CourseService;
import edu.fudan.main.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public CourseController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @PostMapping
    @Authorization
    ResponseEntity<CourseMetaResp> createCourse(@CurrentUser User currentUser,
                                                @Valid @RequestBody CreateCourseReq createCourseReq) {
        return new ResponseEntity<>(courseService.createCourse(
                currentUser, createCourseReq.getName(), createCourseReq.getCode()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cid}")
    @Authorization
    ResponseEntity deleteCourse(@CurrentUser User currentUser,
                                @PathVariable long cid) {
        courseService.deleteCourse(currentUser, cid);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cid}")
    ResponseEntity<CourseMetaResp> getCourse(@PathVariable long cid) {
        return new ResponseEntity<>(courseService.getCourseData(cid), HttpStatus.OK);
    }

    @PutMapping("/{cid}")
    @Authorization
    ResponseEntity<CourseMetaResp> updateCourse(@CurrentUser User currentUser,
                                                @PathVariable long cid,
                                                @RequestBody CreateCourseReq createCourseReq) {
        return new ResponseEntity<>(courseService.updateCourse(currentUser, cid,
                createCourseReq.getName(), createCourseReq.getCode()), HttpStatus.OK);
    }

    @GetMapping("/{cid}/students")
    ResponseEntity<List<UserPublicResp>> getStudentsOfCourse(@PathVariable long cid) {
        return new ResponseEntity<>(courseService.listAllStudentsOfCourse(cid), HttpStatus.OK);
    }

    @PostMapping("/{cid}/students")
    @Authorization
    ResponseEntity<CourseMetaResp> addStudentToCourse(@CurrentUser User currentUser,
                                                      @PathVariable long cid,
                                                      @Valid @RequestBody AddToCourseReq addToCourseReq) {
        return new ResponseEntity<>(courseService.addStudentToCourse(
                currentUser, cid, addToCourseReq.getCode()), HttpStatus.OK);
    }
}
