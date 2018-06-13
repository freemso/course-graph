package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends Neo4jRepository<Student, Long> {

    @Query("MATCH (stu:Student)-[r:STUDENT_OF]->(course:Course) " +
            "WHERE stu.userId = {userId} " +
            "RETURN course ORDER BY course.code")
    List<Course> findCourse(@Param("userId")long userId );

    @Query(value = "MATCH (stu:Student)-[r:STUDENT_OF]->(course:Course) " +
            "WHERE stu.userId = {userId} " +
            "RETURN course ORDER BY course.code",
            countQuery = "MATCH (stu:Student)-[r:STUDENT_OF]->(course:Course) " +
                    "WHERE stu.userId = {userId} " +
                    "RETURN count(*)")
    Page<Course> findCourse(@Param("userId")long userId, Pageable pageable);


    @Query("MATCH (stu:Student)-[r:STUDENT_OF]->(course:Course) " +
            "WHERE stu.userId = {userId} " +
            "RETURN course as courses, count(*) as courseNum ")
    UserCourses findUserCourseInfo(@Param("userId")long userId);
    @QueryResult
    class UserCourses{
        List<Course> courses;
        int courseNum;
    }

    Optional<Student> findById(long id);

    Optional<Student> findByEmail(String email);

}
