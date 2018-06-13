package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.domain.Student;
import edu.fudan.main.domain.Teacher;
import edu.fudan.main.dto.response.CourseMetaResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends Neo4jRepository<Course, Long> {

    //find course
    Optional<Course> findByName(String name);

    Optional<Course> findById(Long id);

    boolean existsById(Long id);

    boolean existsByCode(String courseCode);

    Optional<Course> findByCode(String code);

    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.courseId = {id} " +
            "RETURN student ORDER BY student.name")
    Set<Student> findStudentsById(@Param("id") Long id);

    @Query(value = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.courseId = {id} " +
            "RETURN student ORDER BY student.name",
            countQuery = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
                    "WHERE course.courseId = {id} " +
                    "RETURN count(*)")
    Page<Student> findStudentsById(@Param("id") Long id, Pageable page);


    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.name = {name} " +
            "RETURN student ORDER BY student.name")
    Set<Student> findStudentsByName(@Param("name") String name);

    @Query(value = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.name = {name} " +
            "RETURN student ORDER BY student.name",
            countQuery = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
                    "WHERE course.name = {name} " +
                    "RETURN count(*)")
    Page<Student> findStudentsByName(@Param("name") String name, Pageable page);


    @Query("MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.code = {code} " +
            "RETURN student ORDER BY student.name")
    Set<Student> findStudentsByCode(@Param("code") String code);

    @Query(value = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
            "WHERE course.code = {code} " +
            "RETURN student ORDER BY student.name",
            countQuery = "MATCH (course:Course)<-[r:STUDENT_OF]-(student:Student) " +
                    "WHERE course.code = {code} " +
                    "RETURN count(*)")
    Page<Student> findStudentsByCode(@Param("code") String code, Pageable page);


    @Query("MATCH (course:Course)<-[r:TEACHER_OF]-(teacher:Teacher) " +
            "WHERE course.courseId = {id} " +
            "RETURN teacher")
    Teacher findTeacherByCourseId(@Param("id") long id);

    @Query("MATCH (course:Course)<-[r:TEACHER_OF]-(teacher:Teacher) " +
            "WHERE course.code = {code} " +
            "RETURN teacher")
    Set<Teacher> findTeacherByCode(@Param("code") String code);

    @Query("MATCH (course:Course)<-[r:TEACHER_OF]-(teacher:Teacher) " +
            "WHERE course.name = {name} " +
            "RETURN teacher")
    Teacher findTeacherByName(@Param("name") String name);

    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:CourseGraph) " +
            "WHERE course.courseId = {id} " +
            "RETURN courseGraph ORDER BY courseGraph.name")
    List<CourseGraph> findGraphsById(@Param("id") long id);

    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:CourseGraph) " +
            "WHERE course.name = {name} " +
            "RETURN courseGraph ORDER BY courseGraph.name")
    List<CourseGraph> findGraphsByName(@Param("name") String name);

    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:CourseGraph) " +
            "WHERE course.code = {code} " +
            "RETURN courseGraph ORDER BY courseGraph.name")
    List<CourseGraph> findGraphByCode(@Param("code") String code);

    @Query( "MATCH (course:Course) " +
            "WHERE course.courseId = {id} " +
            "MATCH (course)<-[STUDENT_OF]-(student:Student)"+
            "MATCH (course)<-[r:TEACHER_OF]-(teacher:Teacher)"+
            "RETURN course.name AS name, course.courseId AS id, teacher.name AS teacherName," +
            "teacher.userId AS teacherId, course.createdTime AS createdTime, course.modifiedTime AS modifiedTime" +
            "count(student)")
    CourseMetaResp getCourseMetaById(@Param("id")Long id);


    @Query("MATCH (course:Course)" +
            "WHERE course.courseId = {id}+" +
            "MATCH (course)")
    void deleteById(@Param("id") Long courseId);


}


