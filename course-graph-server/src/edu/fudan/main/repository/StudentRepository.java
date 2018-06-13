package edu.fudan.main.repository;

import edu.fudan.main.domain.Student;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StudentRepository extends Neo4jRepository<Student, Long> {

}
