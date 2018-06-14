package edu.fudan.main.rest;

import edu.fudan.main.annotation.Authorization;
import edu.fudan.main.annotation.CurrentUser;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.request.CreateGraphReq;
import edu.fudan.main.dto.request.JoinCourseReq;
import edu.fudan.main.dto.request.CreateCourseReq;
import edu.fudan.main.dto.request.UpdateCourseMetaReq;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.dto.response.GraphMetaResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.model.CourseService;
import edu.fudan.main.model.GraphService;
import edu.fudan.main.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final UserService userService;

    private final CourseService courseService;

    private final GraphService graphService;

    @Autowired
    public CourseController(UserService userService, CourseService courseService,
                            GraphService graphService) {
        this.userService = userService;
        this.courseService = courseService;
        this.graphService = graphService;
    }

    @GetMapping
    ResponseEntity<List<CourseMetaResp>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
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
        return new ResponseEntity<>(courseService.getCourse(cid), HttpStatus.OK);
    }

    @PutMapping("/{cid}")
    @Authorization
    ResponseEntity<CourseMetaResp> updateCourse(@CurrentUser User currentUser,
                                                @PathVariable long cid,
                                                @RequestBody UpdateCourseMetaReq updateCourseMetaReq) {
        return new ResponseEntity<>(courseService.updateCourse(currentUser, cid,
                updateCourseMetaReq.getName(), updateCourseMetaReq.getCode()), HttpStatus.OK);
    }

    @GetMapping("/{cid}/students")
    ResponseEntity<List<UserPublicResp>> getStudentsOfCourse(@PathVariable long cid) {
        return new ResponseEntity<>(courseService.getAllStudentsOfCourse(cid), HttpStatus.OK);
    }

    @PostMapping("/{cid}/students")
    @Authorization
    ResponseEntity<CourseMetaResp> addStudentToCourse(@CurrentUser User currentUser,
                                                      @PathVariable long cid,
                                                      @Valid @RequestBody JoinCourseReq joinCourseReq) {
        return new ResponseEntity<>(courseService.addStudentToCourse(
                currentUser, cid, joinCourseReq.getCode()), HttpStatus.OK);
    }

    @GetMapping("/{cid}/graphs")
    @Authorization
    ResponseEntity<List<GraphMetaResp>> getGraphsOfCourse(@PathVariable long cid,
                                                          @CurrentUser User currentUser) {
        return new ResponseEntity<>(graphService.getAllGraphsOfCourse(currentUser, cid), HttpStatus.OK);
    }

    @PostMapping("{cid}/graphs")
    @Authorization
    ResponseEntity<GraphMetaResp> addGraphsToCourse(@CurrentUser User currentUser,
                                                    @PathVariable long cid,
                                                    @Valid @RequestBody CreateGraphReq createGraphReq) {
        return new ResponseEntity<>(graphService.createNewGraph(currentUser, createGraphReq.getName(),
                createGraphReq.getDescription(), createGraphReq.getJsmind(), cid), HttpStatus.CREATED);
    }
}
