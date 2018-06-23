package edu.fudan.repository;

import edu.fudan.domain.Question;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestionRepository extends Neo4jRepository<Question, Long>{
}
