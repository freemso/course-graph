package edu.fudan.repository;

import edu.fudan.domain.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StudentRepository extends Neo4jRepository<Student, Long> {

}
