package edu.fudan.main.repository;

import edu.fudan.main.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends UserRepository{

    @Query("MATCH (teacher:Teacher)-[r:TEACHER_OF]->(course:Course) " +
            "WHERE teacher.userId = {userId} " +
            "RETURN course ORDER BY course.code")
    List<Course> findCourse(@Param("userId")long userId);

    @Query(value = "MATCH (teacher:Teacher)-[r:TEACHER_OF]->(course:Course) " +
            "WHERE teacher.userId = {userId} " +
            "RETURN course ORDER BY course.code",
            countQuery = "MATCH (teacher:Teacher)-[r:TEACHER_OF]->(course:Course) " +
                    "WHERE teacher.userId = {userId} " +
                    "RETURN count(*)")
    Page<Course> findCourse(@Param("userId")long userId, Pageable pageable);

}
