package edu.fudan.main.model;

import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.User;
import edu.fudan.main.exception.CourseNotFoundException;
import edu.fudan.main.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {

    private final CourseRepository courseRepository;

    public PermissionService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Check write permission of the course
     * @return true is have permission, false if not
     */
    public boolean checkWritePermOfCourse(User user, long courseId) {
        // The course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // User must be the owner/teacher of the course
        return course.getTeacher().equals(user);
    }

    /**
     * Check read permission of the course
     * @return true is have permission, false if not
     */
    public boolean checkReadPermOfCourse(User user, long courseId) {
        // The course must first exist
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException(courseId)
        );

        // Owner/teacher of the course have the read permission
        if (checkWritePermOfCourse(user, courseId)) return true;

        return course.getStudents().contains(user);
    }
}
