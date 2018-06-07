package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.Teacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends Neo4jRepository<Course, Long> {

    //find course
    Optional<Course> findByName(String name);
    Optional<Course> findById(Long id);
    Optional<Course> findByCode(String code);

    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.courseId = {id} " +
            "RETURN student")
    Set<Student> getStudentsById(@Param("id") Long id);

    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.name = {name} " +
            "RETURN student")
    Set<Student>  getStudentsByName(@Param("name") String name);

    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.code = {code} " +
            "RETURN student")
    Set<Student>  getStudentsByCode(@Param("code") String code);








}

