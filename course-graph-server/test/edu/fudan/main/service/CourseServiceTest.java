package edu.fudan.main.service;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Teacher;
import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.exception.CourseConflictException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.model.CourseService;
import edu.fudan.main.model.UserService;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.StudentRepository;
import edu.fudan.main.repository.TeacherRepository;
import edu.fudan.main.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StudentRepository studentRepository;

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

    @Test
    public void testCreateCourse() {
        // Teacher can create a course
        CourseMetaResp courseMetaResp1 = courseService.createCourse(
                teacherUser, "some course", "course_code");
        assertNotNull(courseMetaResp1);
        assertEquals(courseMetaResp1.getCode(), "course_code");
        assertEquals(courseMetaResp1.getName(), "some course");
        assertEquals(courseMetaResp1.getStudentNum(), 0);
        assertEquals(courseMetaResp1.getTeacherId(), teacherUser.getId());
        assertEquals(courseMetaResp1.getTeacherName(), teacherUser.getName());

        // Abort the create time / modified time test
//        assertEquals(courseMetaResp1.getCreatedTime(), courseMetaResp1.getModifiedTime());
//        // create time must before now and after one hour ago
//        assertTrue(courseMetaResp1.getCreatedTime().before(new Date()));
//        Date oneHourAgo = new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1));
//        assertTrue(courseMetaResp1.getCreatedTime().after(oneHourAgo));

        // Get the newly created course
        Course course1 = courseRepository.findById(courseMetaResp1.getId()).orElse(null);
        assertNotNull(course1);

        // Teacher can find a course in his course list
        Teacher teacher = teacherRepository.findById(teacherUser.getId()).orElse(null);
        assertNotNull(teacher);
        assertEquals(teacher.getCourseList().size(), 1);
        assertEquals(teacher.getCourseList().get(0), course1);

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
        CourseMetaResp courseMetaResp1 = courseService.createCourse(
                teacherUser, "some course", "course_code");

        long courseId = courseMetaResp1.getId();

        // Student or other users cannot delete the course
        try {
            courseService.deleteCourse(studentUser, courseId);
            fail();
        } catch (PermissionDeniedException ignore) {}

        // Teacher himself can delete the course
        assertTrue(courseRepository.findById(courseId).isPresent());
        Course course1 = courseRepository.findById(courseMetaResp1.getId()).orElse(null);
        assertNotNull(course1);
        courseService.deleteCourse(teacherUser, courseId);
        assertFalse(courseRepository.findById(courseId).isPresent());

        // Teacher can find his course list empty again
        Teacher teacher = teacherRepository.findById(teacherUser.getId()).orElse(null);
        assertNotNull(teacher);
        assertEquals(teacher.getCourseList().size(), 0);

//        Course course2 = teacher.getCourseList().get(0);
//        assertEquals(course1, course2);
//        System.out.println(course2.getCourseId());
//        System.out.println(courseRepository.findById(course2.getCourseId()).isPresent());
    }
}
