package edu.fudan.main.repository;

import edu.fudan.main.domain.CourseGraph;
import edu.fudan.main.dto.response.GraphMetaResp;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GraphRepository extends Neo4jRepository<CourseGraph, Long> {

    @Query("MATCH (graph:CourseGraph) " +
            "WHERE graph.id = {id} " +
            "graph.id AS id, graph.name AS name")
    GraphMetaResp getGraphMetaById(@Param("id")long id);


    boolean existsByCourseName(String courseName);


}
