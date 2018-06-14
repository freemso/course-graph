package edu.fudan.main.repository;

import edu.fudan.main.domain.Graph;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphRepository extends Neo4jRepository<Graph, Long> {

    @Query("MATCH (c:Course)<-[r:GRAPH_OF]-(g:Graph) " +
            "WHERE c.courseId = {courseId} and g.name = {courseName} " +
            "return count(g) > 0")
    boolean existsByName(@Param("courseId") String name, @Param("courseName") long courseId);


    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:Graph) " +
            "WHERE course.courseId = {id} " +
            "RETURN courseGraph ORDER BY courseGraph.name")
    List<Graph> findGraphsByCourseId(@Param("id") long id);

    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:Graph) " +
            "WHERE course.name = {name} " +
            "RETURN courseGraph ORDER BY courseGraph.name")
    List<Graph> findGraphsByCourseName(@Param("name") String name);

    @Query("MATCH (course:Course)<-[r:GRAPH_OF]-(courseGraph:Graph) " +
            "WHERE course.code = {code} " +
            "RETURN courseGraph ORDER BY courseGraph.name ")
    List<Graph> findGraphsByCourseCode(@Param("code") String code);


}
