package edu.fudan.main.repository;


import edu.fudan.main.domain.Course;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashMap;

public interface CourseRepository extends Neo4jRepository<Course, Long> {

    @Query("match p = (n1:CourseNode)-[r:CHILDREN*]->(n2:CourseNode)\n" +
            "where ID(n1) = {rootId}\n" +
            "with collect(p) as ps\n" +
            "call apoc.convert.toTree(ps) yield value\n" +
            "return value;")
    LinkedHashMap fetchCourseModuleTreeByRootId(@Param("rootId")long id);



}
