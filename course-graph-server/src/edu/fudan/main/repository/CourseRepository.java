package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends Neo4jRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    boolean existsByCode(String code);

    void deleteByCode(String code);

    /**
     * List all courses that a student takes, ordered by course name
     *
     * @param studentId, user id of the student
     * @return a list of courses
     */
    @Query("MATCH (student:Student)-[r:STUDENT_OF]->(course:Course) " +
            "WHERE student.userId = {id} " +
            "RETURN course ORDER BY course.name")
    @Depth(value = 1)
    List<Course> findCoursesOfStudent(@Param("id") long studentId);

    /**
     * List all courses that a teacher teaches, ordered by course name
     *
     * @param teacherId, user id of the teacher
     * @return a list of courses
     */
    @Query("MATCH (teacher:Teacher)-[r:TEACHER_OF]->(course:Course) " +
            "WHERE teacher.userId = {id} " +
            "RETURN course ORDER BY course.name")
    @Depth(value = 1)
    List<Course> findCoursesOfTeacher(@Param("id") long teacherId);
}
