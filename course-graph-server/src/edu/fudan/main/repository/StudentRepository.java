package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends UserRepository{


    @Query("MATCH (stu:Student)-[r:STUDENT_OF]->(course:Course) " +
            "WHERE stu.userId = {userId} " +
            "RETURN course ORDER BY course.code")
    List<Course> findCourse(@Param("userId")long userId );



}
