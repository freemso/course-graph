package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Graph;
import edu.fudan.main.domain.User;
import edu.fudan.main.domain.UserType;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.dto.response.UserPublicResp;
import edu.fudan.main.exception.*;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.StudentRepository;
import edu.fudan.main.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final GraphService graphService;

    private final PermissionService permissionService;

    @Autowired
    public CourseService(CourseRepository courseRepository, GraphService graphService,
                         StudentRepository studentRepository, TeacherRepository teacherRepository,
                         PermissionService permissionService) {
        this.courseRepository = courseRepository;
        this.graphService = graphService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.permissionService = permissionService;
    }

    /**
     * Create a new course
     * @param currentUser current login user, must be a teacher
     * @param courseName name of the new course
     * @param courseCode code of the new course
     * @return CourseMetaResp
     */
    public CourseMetaResp createCourse(User currentUser, String courseName, String courseCode) {
        // Current user must be a teacher
        if (currentUser.getType() != UserType.TEACHER) {
            throw new PermissionDeniedException();
        }

        // Course code must be unique
        if (courseRepository.findByCode(courseCode).isPresent()) {
            throw new CourseConflictException(courseCode);
        }

        // Generate a new id for the course
        long newCourseId = RandomIdGenerator.getInstance().generateRandomLongId(courseRepository);

        Course course = courseRepository.save(new Course(courseCode, courseName, newCourseId,
                teacherRepository.findById(currentUser.getId()).orElseThrow(UserNotFoundException::new)));

        return new CourseMetaResp(course);
    }

    /**
     * Delete a course by course id
     * @param currentUser, current login user
     * @param courseId, id of the course to delete
     */
    public void deleteCourse(User currentUser, long courseId){
        // The course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Current user must be the owner/teacher of the course
        if (!permissionService.checkWritePermOfCourse(currentUser, courseId)) {
            throw new PermissionDeniedException();
        }

        // Delete all graphs of the course
        for (Graph g : course.getGraphList()) {
            graphService.deleteGraph(g.getGraphId());
        }

        // Delete the relation to teacher
        course.removeTeacher();
        courseRepository.save(course);

        // Delete the course
        courseRepository.delete(course);
    }

    /**
     * List all courses of a user.
     * List the courses that the user takes if it's a student.
     * List the courses that the user teaches if it's a teacher.
     * @param currentUser, current login user
     * @return list of course meta data
     */
    public List<CourseMetaResp> getAllCoursesOfUser(User currentUser){
        List<Course> courses = new ArrayList<>();

        switch (currentUser.getType()) {
            case TEACHER:
                courses = teacherRepository.findById(currentUser.getId()).orElseThrow(
                        UserNotFoundException::new
                ).getCourseList();
                break;
            case STUDENT:
                courses = studentRepository.findById(currentUser.getId()).orElseThrow(
                        UserNotFoundException::new
                ).getCourseList();
                break;
        }

        List<CourseMetaResp> courseMetaRespList = new ArrayList<>();

        for (Course c : courses) {
            courseMetaRespList.add(new CourseMetaResp(c));
        }

        return courseMetaRespList;
    }

    /**
     * List all courses.
     * @return list of courses
     */
    public List<CourseMetaResp> getAllCourses() {
        List<CourseMetaResp> courseMetaRespList = new ArrayList<>();
        for (Course c : courseRepository.findAll()) {
            courseMetaRespList.add(new CourseMetaResp(c));
        }

        return courseMetaRespList;
    }

    /**
     * Get meta data of a course
     * @param courseId, id of the course
     * @return course meta info
     */
    public CourseMetaResp getCourse(Long courseId){
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );
        return new CourseMetaResp(course);
    }

    /**
     * Update course meta data
     * @param courseId, id of the course
     * @param name course name #optimal
     * @param code course code #optimal
     */
    public CourseMetaResp updateCourse(User currentUser, long courseId, String name, String code){
        // First the course must exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Current user must be the owner/teacher of the course
        if (!permissionService.checkWritePermOfCourse(currentUser, courseId)) {
            throw new PermissionDeniedException();
        }

        if (name != null) {
            course.setName(name);
        }

        if (code != null) {
            // Check if code is conflict with other courses
            if (courseRepository.findByCode(code).isPresent()) {
                throw new CourseConflictException(code);
            }
            course.setCode(code);
        }

        // Save the change
        return new CourseMetaResp(courseRepository.save(course));
    }

    /**
     * Get all students of this course
     * @param courseId, id of the course
     * @return all students of this course
     */
    public List<UserPublicResp> getAllStudentsOfCourse(long courseId){
        // First course must exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        List<UserPublicResp> results = new ArrayList<>();

        for (User u : course.getStudents()) {
            results.add(new UserPublicResp(u));
        }
        return results;
    }

    /**
     * Add a student to a course.
     * Need to check if the code provided matches course code.
     * @param currentUser, current login user
     * @param courseId, id of the course
     * @param code, code provided
     * @return meta data of the course
     */
    public CourseMetaResp addStudentToCourse(User currentUser, long courseId, String code) {
        // Fist the course must exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Code must be the same as course code
        if (!code.equals(course.getCode())) {
            throw new InvalidCodeException();
        }

        // Current user must be a student
        if (currentUser.getType() != UserType.STUDENT) {
            throw new PermissionDeniedException();
        }

        // Current user must not be in the course
        if (course.getStudents().contains(currentUser)) {
            throw new DuplicateStudentException();
        }

        // Add to course
        course.addAStudent(studentRepository.findById(currentUser.getId()).orElseThrow(
                UserNotFoundException::new
        ));

        return new CourseMetaResp(courseRepository.save(course));

    }

}
