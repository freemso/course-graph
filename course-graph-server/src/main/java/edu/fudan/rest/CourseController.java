package edu.fudan.rest;

import edu.fudan.annotation.Authorization;
import edu.fudan.annotation.CurrentUser;
import edu.fudan.domain.User;
import edu.fudan.dto.request.CreateCourseReq;
import edu.fudan.dto.request.CreateGraphReq;
import edu.fudan.dto.request.JoinCourseReq;
import edu.fudan.dto.request.UpdateCourseMetaReq;
import edu.fudan.dto.response.CourseMetaResp;
import edu.fudan.dto.response.CoursePublicResp;
import edu.fudan.dto.response.GraphMetaResp;
import edu.fudan.dto.response.UserPublicResp;
import edu.fudan.model.CourseService;
import edu.fudan.model.GraphService;
import edu.fudan.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@CrossOrigin
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
    @Authorization
    ResponseEntity<List<CoursePublicResp>> getAllCourses(@CurrentUser User curretnUser) {
        return new ResponseEntity<>(courseService.getAllCourses(curretnUser), HttpStatus.OK);
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
    @Authorization
    ResponseEntity<CourseMetaResp> getCourse(@CurrentUser User currentUser,
                                             @PathVariable long cid) {
        return new ResponseEntity<>(courseService.getCourse(currentUser, cid), HttpStatus.OK);
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
    @Authorization
    ResponseEntity<List<UserPublicResp>> getStudentsOfCourse(@CurrentUser User currentUser,
                                                             @PathVariable long cid) {
        return new ResponseEntity<>(courseService.getAllStudentsOfCourse(currentUser, cid), HttpStatus.OK);
    }

    @PostMapping("/{cid}/students")
    @Authorization
    ResponseEntity<CourseMetaResp> addStudentToCourse(@CurrentUser User currentUser,
                                                      @PathVariable long cid,
                                                      @Valid @RequestBody JoinCourseReq joinCourseReq) {
        return new ResponseEntity<>(courseService.addStudentToCourse(
                currentUser, cid, joinCourseReq.getCode()), HttpStatus.CREATED);
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
