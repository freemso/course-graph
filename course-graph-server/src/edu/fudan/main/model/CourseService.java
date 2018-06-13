package edu.fudan.main.model;

import edu.fudan.main.domain.*;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.exception.CourseConflictException;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.exception.PermissionDeniedException;
import edu.fudan.main.exception.UserNotFoundException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.StudentRepository;
import edu.fudan.main.repository.TeacherRepository;
import edu.fudan.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    private final GraphService graphService;

    @Autowired
    public CourseService(CourseRepository courseRepository, GraphService graphService,
                         StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.graphService = graphService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }


    /**
     * Create a new course
     *
     * @param currentUser current login user, must be a teacher
     * @param courseName name of the new course
     * @param courseCode code of the new course
     * @return CourseMetaResp
     */
    public CourseMetaResp addCourse(User currentUser, String courseName, String courseCode) {
        // Current user must be a teacher
        if (currentUser.getType() != UserType.TEACHER) {
            throw new PermissionDeniedException();
        }

        // Course code must be unique
        if (courseRepository.existsByCode(courseCode)) {
            throw new CourseConflictException(courseCode);
        }

        // Generate a new id for the course
        long newCourseId = RandomIdGenerator.getInstance().generateRandomLongId(courseRepository);

        Course course = courseRepository.save(new Course(courseCode, courseName, newCourseId, (Teacher) currentUser));

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
        if (currentUser.getType() != UserType.TEACHER || !course.getTeacher().equals(currentUser)) {
            throw new PermissionDeniedException();
        }

        // Delete all graphs of the course
        for (Graph g : course.getGraphList()) {
            graphService.deleteGraph(g.getGraphId());
        }

        // Delete the course
        courseRepository.deleteById(courseId);
    }

    public List<CourseMetaResp> listAllCourses(User currentUser){
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
     *
     * @param courseId
     * @return course meta info
     */
    public CourseMetaResp getCourseData(Long courseId){
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );
        return new CourseMetaResp(course);
    }

    /**
     *
     * @param courseId
     * @param name course name #optimal
     * @param code course code #optimal
     * @return
     */
    public CourseMetaResp updateCourse(Long courseId, String name, String code){
        Optional<Course> course = courseRepository.findById(courseId);
        if(!course.isPresent())
            throw new CourseNotFoundException(courseId);

        Course course1 = course.get();
        if(name != null)
            course1.setName(name);
        if(code != null){
            if(courseRepository.existsByCode(code))
                throw new CourseConflictException(code);
            course1.setCode(code);
        }
        courseRepository.save(course1);
        return null;
    }


    /**
     * get all students of this course
     * @param courseId
     * @return all students of this course
     */
    public Set<Student> listAllStudents(long courseId){
        Optional<Course> course = courseRepository.findById(courseId);
        if(!course.isPresent())
            throw new CourseNotFoundException(courseId);
        return courseRepository.findStudentsById(courseId);
    }


    /**
     * Generate a unique course id
     *
     * @return a user id
     */
    private long generateRandomId() {
        while (true) {
            long randomLong = RandomIdGenerator.getInstance().generateRandomLongId();
            // Check if the id exists as a course id
            if (!this.courseRepository.existsById(randomLong)) {
                return randomLong;
            }
        }
    }

}
