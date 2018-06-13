package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends UserRepository{

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

}
