package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.Teacher;
import edu.fudan.main.domain.User;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.exception.CourseConflictException;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.StudentRepository;
import edu.fudan.main.repository.TeacherRepository;
import edu.fudan.main.util.RandomIdGenerator;
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
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository =  teacherRepository;
    }


    /**
     * teacher add a new course
     *
     * @param courseName name of the new course
     * @param courseCode code of the new course
     * @param teacher
     * @return CourseMetaResp
     */
    public CourseMetaResp addCourse(String courseName, String courseCode, Teacher teacher) {
        if (courseRepository.existsByCode(courseCode))
            throw new CourseConflictException(courseCode);
        long courseId = generateRandomId();
        Course course = new Course(courseCode, courseName, courseId);
        teacher.addCourse(course);
        courseRepository.save(course);
        teacherRepository.save(teacher, 0);
        return courseRepository.getCourseMetaById(courseId);
    }

    /**
     * delete a course by course id
     * @param courseId
     * @return true if succeeds else return false
     */
    public boolean deleteCourse(Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);
        if(!course.isPresent())
            throw new CourseNotFoundException(courseId);

        courseRepository.deleteById(courseId);
        return true;
    }


    public List<CourseMetaResp> listAllCourses(Student student){
        List<Course> courses = student.getCourseList();
        List<CourseMetaResp> courseMetaResps = new ArrayList<>();
        for(Course c: courses){
            courseMetaResps.add(courseRepository.getCourseMetaById(c.getCourseId()));
        }
        return courseMetaResps;
    }


    /**
     *
     * @param courseId
     * @return course meta info
     */
    public CourseMetaResp getCourseData(Long courseId){
        if(!courseRepository.findById(courseId).isPresent())
            throw new CourseNotFoundException(courseId);
        return courseRepository.getCourseMetaById(courseId);
    }
    //for students

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
