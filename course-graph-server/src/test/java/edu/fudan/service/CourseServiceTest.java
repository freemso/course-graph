package edu.fudan.service;


import edu.fudan.domain.Course;
import edu.fudan.domain.Teacher;
import edu.fudan.domain.User;
import edu.fudan.domain.UserType;
import edu.fudan.dto.response.CourseMetaResp;
import edu.fudan.dto.response.UserPublicResp;
import edu.fudan.exception.*;
import edu.fudan.model.CourseService;
import edu.fudan.model.UserService;
import edu.fudan.repository.CourseRepository;
import edu.fudan.repository.TeacherRepository;
import edu.fudan.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private User teacherUser, studentUser;

    @Before
    public void setup() {
        // Create a new teacher and a student
        userService.createUser("teacher@some.com", "teacher", "somePassword$$42", UserType.TEACHER);
        userService.createUser("student@some.com", "student", "somePassword$$42", UserType.STUDENT);

        // Get the user from repository
        teacherUser = userRepository.findByEmail("teacher@some.com").orElse(null);
        studentUser = userRepository.findByEmail("student@some.com").orElse(null);
        assertNotNull(teacherUser);
        assertNotNull(studentUser);
    }

    @After
    public void after() {
        userRepository.deleteById(teacherUser.getUserId());
        userRepository.deleteById(studentUser.getUserId());
    }

    @Test
    public void testCreateCourse() {
        // Teacher can create a course
        CourseMetaResp courseMetaResp = courseService.createCourse(
                teacherUser, "some course", "course_code");
        assertNotNull(courseMetaResp);
        assertEquals(courseMetaResp.getCode(), "course_code");
        assertEquals(courseMetaResp.getName(), "some course");
        assertEquals(courseMetaResp.getStudentNum(), 0);
        assertEquals(courseMetaResp.getTeacherId(), teacherUser.getUserId());
        assertEquals(courseMetaResp.getTeacherName(), teacherUser.getName());

        // Abort the create time / modified time test
//        assertEquals(courseMetaResp1.getCreatedTime(), courseMetaResp1.getModifiedTime());
//        // create time must before now and after one hour ago
//        assertTrue(courseMetaResp1.getCreatedTime().before(new Date()));
//        Date oneHourAgo = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));
//        assertTrue(courseMetaResp1.getCreatedTime().after(oneHourAgo));

        // Get the newly created course
        Course course = courseRepository.findById(courseMetaResp.getId()).orElse(null);
        assertNotNull(course);

        // Teacher can find a course in his course list
        Teacher teacher = teacherRepository.findById(teacherUser.getUserId()).orElse(null);
        assertNotNull(teacher);
        assertEquals(teacher.getCourseList().size(), 1);
        assertEquals(teacher.getCourseList().get(0), course);

        // Can not create another course with the same code
        try {
            courseService.createCourse(
                    teacherUser, "some another course", "course_code"
            );
            fail();
        } catch (CourseConflictException ignore) {}

        // Student can not create a course
        try {
            courseService.createCourse(
                    studentUser, "some another course", "course_another_code"
            );
            fail();
        } catch (PermissionDeniedException ignore) {}
    }

    @Test
    public void testDeleteCourse() {
        CourseMetaResp courseMetaResp = courseService.createCourse(
                teacherUser, "some course", "course_code");

        long courseId = courseMetaResp.getId();

        // Student or other users cannot delete the course
        try {
            courseService.deleteCourse(studentUser, courseId);
            fail();
        } catch (PermissionDeniedException ignore) {}

        // Teacher himself can delete the course
        assertTrue(courseRepository.findById(courseId).isPresent());
        Course course = courseRepository.findById(courseMetaResp.getId()).orElse(null);
        assertNotNull(course);
        courseService.deleteCourse(teacherUser, courseId);
        assertFalse(courseRepository.findById(courseId).isPresent());

        // Teacher can find his course list empty again
        Teacher teacher = teacherRepository.findById(teacherUser.getUserId()).orElse(null);
        assertNotNull(teacher);

//        assertEquals(teacher.getCourseList().size(), 0);

//        Course course2 = teacher.getCourseList().get(0);
//        assertEquals(course1, course2);
//        System.out.println(course2.getCourseId());
//        System.out.println(courseRepository.findById(course2.getCourseId()).isPresent());
    }

    @Test
    public void testUpdateCourse() {
        // Teacher create a course
        CourseMetaResp courseMetaResp = courseService.createCourse(
                teacherUser, "some course", "course_code");

        // Teacher can update the course
        courseMetaResp = courseService.updateCourse(
                teacherUser, courseMetaResp.getId(), "another name", "another_code");
        assertEquals(courseMetaResp.getName(), "another name");
        assertEquals(courseMetaResp.getCode(), "another_code");
        // Can update part of the course
        courseMetaResp = courseService.updateCourse(
                teacherUser, courseMetaResp.getId(), "yet another name", null);
        assertEquals(courseMetaResp.getName(), "yet another name");
        assertEquals(courseMetaResp.getCode(), "another_code");

        // Student or others can not update the course
        try {
            courseService.updateCourse(studentUser, courseMetaResp.getId(), "some", "some_code");
            fail();
        } catch (PermissionDeniedException ignore) {}
        assertEquals(courseMetaResp.getName(), "yet another name");
        assertEquals(courseMetaResp.getCode(), "another_code");

    }

    @Test
    public void testAddStudentToCourse() {
        // Teacher create a course
        CourseMetaResp courseMetaResp = courseService.createCourse(
                teacherUser, "some course", "course_code");

        assertEquals(courseMetaResp.getStudentNum(), 0);

        // Add student to course
        // wrong id
        try {
            courseService.addStudentToCourse(studentUser, 1234, "course_code");
            fail();
        } catch (CourseNotFoundException ignore) {}
        // correct id, wrong code
        try {
            courseService.addStudentToCourse(studentUser, courseMetaResp.getId(), "wrong_code");
            fail();
        } catch (InvalidCodeException ignore) {}
        // current user not a student
        try {
            courseService.addStudentToCourse(teacherUser, courseMetaResp.getId(), courseMetaResp.getCode());
            fail();
        } catch (PermissionDeniedException ignore) {}

        courseMetaResp = courseService.addStudentToCourse(
                studentUser, courseMetaResp.getId(), courseMetaResp.getCode());

        assertEquals(courseMetaResp.getStudentNum(), 1);

        // can not add it again
        try {
            courseService.addStudentToCourse(
                    studentUser, courseMetaResp.getId(), courseMetaResp.getCode());
            fail();
        } catch (DuplicateStudentException ignore) {}

        Course course = courseRepository.findById(courseMetaResp.getId()).orElse(null);
        assertNotNull(course);

        assertEquals(course.getStudents().size(), 1);
        assertTrue(course.getStudents().contains(studentUser));

    }

    @Test
    public void testGetCoursesOfUserAndGetStudentsOfCourse() {
        // Teacher create a course
        CourseMetaResp courseMetaResp = courseService.createCourse(
                teacherUser, "some course", "course_code");
        courseMetaResp = courseService.addStudentToCourse(
                studentUser, courseMetaResp.getId(), courseMetaResp.getCode());

        // Create another student
        userService.createUser(
                "student2@some.com", "student2", "somePassword$$42", UserType.STUDENT);
        User anotherStudent = userRepository.findByEmail("student2@some.com").orElse(null);
        assertNotNull(anotherStudent);
        // Add it to course
        courseMetaResp = courseService.addStudentToCourse(
                anotherStudent, courseMetaResp.getId(), courseMetaResp.getCode());

        // Teacher add another course
        CourseMetaResp anotherCourseMetaResp = courseService.createCourse(
                teacherUser, "another course", "another_code"
        );
        // Add newly created student to this newly created course
        anotherCourseMetaResp = courseService.addStudentToCourse(
                anotherStudent, anotherCourseMetaResp.getId(), anotherCourseMetaResp.getCode()
        );

        // Get all courses of the teacher
        List<CourseMetaResp> teacherCourseList = courseService.getAllCoursesOfUser(teacherUser);
        assertNotNull(teacherCourseList);
        assertEquals(teacherCourseList.size(), 2);

        // Get all courses of the student
        List<CourseMetaResp> studentCourseList = courseService.getAllCoursesOfUser(studentUser);
        assertNotNull(studentCourseList);
        assertEquals(studentCourseList.size(), 1);

        // Get all courses of another student
        List<CourseMetaResp> anotherStudentCourseList = courseService.getAllCoursesOfUser(anotherStudent);
        assertNotNull(anotherStudentCourseList);
        assertEquals(anotherStudentCourseList.size(), 2);

        // Get all students of a course
        List<UserPublicResp> studentList = courseService.getAllStudentsOfCourse(teacherUser, courseMetaResp.getId());
        assertNotNull(studentList);
        assertEquals(studentList.size(), 2);

        // Get all student of another course
        List<UserPublicResp> studentListAnother = courseService.getAllStudentsOfCourse(teacherUser, anotherCourseMetaResp.getId());
        assertNotNull(studentListAnother);
        assertEquals(studentListAnother.size(), 1);

        // Course id not exist
        try {
            courseService.getAllStudentsOfCourse(teacherUser, 42);
            fail();
        } catch (CourseNotFoundException ignore) {}
    }
}
