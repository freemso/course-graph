package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.Teacher;
import edu.fudan.main.dto.response.CourseMetaResp;
import edu.fudan.main.exception.CourseConflictException;
import edu.fudan.main.repository.CourseRepository;
import edu.fudan.main.repository.StudentRepository;
import edu.fudan.main.repository.TeacherRepository;
import edu.fudan.main.util.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    /***course service for teacher***/

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

    public boolean deleteCourse(Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);


        return false;
    }




    //modify a course


    //for students


    /**
     * Generate a unique user id
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
