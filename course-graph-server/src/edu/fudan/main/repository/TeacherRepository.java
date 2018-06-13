package edu.fudan.main.repository;

import edu.fudan.main.domain.Teacher;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TeacherRepository extends Neo4jRepository<Teacher, Long> {
}
