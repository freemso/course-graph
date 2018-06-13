package edu.fudan.main.repository;

import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.domain.CourseGraphMeta;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphRepository extends Neo4jRepository<CourseGraph, Long> {

    @Query("MATCH (graph:CourseGraph) " +
            "WHERE graph.id = {id} " +
            "graph.id AS id, graph.name AS name")
    CourseGraphMeta getGraphMetaById(@Param("id")long id);


    @Query("MATCH (c:Course)<-[r:GRAPH_OF]-(g:CourseGraph)" +
            "WHERE c.courseId = {courseId} and g.name = courseName" +
            "return count(g) > 0")
    boolean existsByCourseName(String courseName, long courseId);


    @Query("MATCH (c:Course)<-[r:GRAPH_OF]-(g:CourseGraph)" +
            "WHERE c.courseId = {courseId}" +
            "return g.courseGraphId as courseGraphId, c.courseId as courseId," +
            "g.courseGraphName as courseGraphName")
    List<CourseGraphMeta> listAllGraphs(@Param("courseId")long courseId);


    //todo
    @Query()
    void deleteCourseGraphByCourseGraphId(@Param("courseGraphId")long courseGraphId);
}
